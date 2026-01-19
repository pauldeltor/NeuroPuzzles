package com.pauldelgado.neuropuzzles.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pauldelgado.neuropuzzles.R
import com.pauldelgado.neuropuzzles.data.AppSettings
import com.pauldelgado.neuropuzzles.data.Card
import com.pauldelgado.neuropuzzles.data.DragonBallRepository
import com.pauldelgado.neuropuzzles.data.Level
import com.pauldelgado.neuropuzzles.game.MemoryGame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.max

class GameActivity : AppCompatActivity() {

    private lateinit var settings: AppSettings
    private val repo = DragonBallRepository()

    private lateinit var tvLevel: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvAttempts: TextView
    private lateinit var progress: ProgressBar
    private lateinit var rv: RecyclerView

    private var timer: CountDownTimer? = null
    private var secondsLeft: Int = 0
    private var elapsedSeconds: Int = 0

    private var lockClicks: Boolean = false
    private lateinit var level: Level

    private lateinit var adapter: CardAdapter
    private lateinit var game: MemoryGame
    private val cards: MutableList<Card> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        settings = AppSettings(this)

        tvLevel = findViewById(R.id.tvLevel)
        tvTime = findViewById(R.id.tvTime)
        tvAttempts = findViewById(R.id.tvAttempts)
        progress = findViewById(R.id.progress)
        rv = findViewById(R.id.rvCards)

        level = Level.valueOf(intent.getStringExtra(EXTRA_LEVEL) ?: Level.EASY.name)
        tvLevel.text = "Nivel: ${level.label}"

        secondsLeft = settings.sessionSeconds
        elapsedSeconds = 0
        updateTime()

        setupRecycler()
        loadGame()
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }

    private fun setupRecycler() {
        val (totalCards, spanCount) = when (level) {
            Level.EASY -> 16 to 4      // 8 pares
            Level.MEDIUM -> 24 to 4    // 12 pares
            Level.HARD -> 36 to 6      // 18 pares
        }

        rv.layoutManager = GridLayoutManager(this, spanCount)

        // adapter is created after cards are built; we initialize with empty list
        adapter = CardAdapter(cards) { pos -> onCardClick(pos) }
        rv.adapter = adapter

        // store totalCards via tag
        rv.tag = totalCards
    }

    private fun loadGame() {
        progress.visibility = View.VISIBLE

        val totalCards = (rv.tag as Int)
        val pairs = totalCards / 2

        lifecycleScope.launch {
            try {
                val characters = withContext(Dispatchers.IO) {
                    repo.fetchCharacters(limit = max(pairs, 10))
                }.shuffled().take(pairs)

                cards.clear()
                characters.forEach { c ->
                    cards += Card(pairId = c.id, imageUrl = c.imageUrl)
                    cards += Card(pairId = c.id, imageUrl = c.imageUrl)
                }
                cards.shuffle()

                game = MemoryGame(cards)
                adapter.notifyDataSetChanged()

                // Preview phase (all face-up), then start timer
                doPreviewThenStart()

            } catch (e: Exception) {
                progress.visibility = View.GONE
                tvLevel.text = "Error cargando imágenes. Revisa Internet."
            }
        }
    }

    private suspend fun doPreviewThenStart() {
        progress.visibility = View.GONE

        val preview = settings.previewSeconds
        if (preview > 0) {
            lockClicks = true
            cards.forEach { it.isFaceUp = true }
            adapter.notifyDataSetChanged()
            delay(preview * 1000L)
            cards.forEach { it.isFaceUp = false }
            adapter.notifyDataSetChanged()
            lockClicks = false
        }

        startTimer()
        updateAttempts()
    }

    private fun startTimer() {
        timer?.cancel()
        timer = object : CountDownTimer((secondsLeft * 1000L), 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                secondsLeft = (millisUntilFinished / 1000L).toInt()
                elapsedSeconds += 1
                updateTime()
            }

            override fun onFinish() {
                secondsLeft = 0
                updateTime()
                finishGame(completed = false)
            }
        }.start()
    }

    private fun updateTime() {
        tvTime.text = "Tiempo: ${secondsLeft}s"
    }

    private fun updateAttempts() {
        tvAttempts.text = "Intentos: ${game.attempts}"
    }

    private fun onCardClick(position: Int) {
        if (lockClicks) return
        if (!::game.isInitialized) return

        val result = game.flip(position) ?: run {
            adapter.notifyCardChanged(position)
            return
        }

        // second card flipped
        adapter.notifyCardsChanged(listOf(result.first, result.second))
        updateAttempts()

        if (!result.isMatch) {
            lockClicks = true
            lifecycleScope.launch {
                delay(700L)
                game.hide(listOf(result.first, result.second))
                adapter.notifyCardsChanged(listOf(result.first, result.second))
                lockClicks = false
            }
        } else {
            if (game.allMatched()) {
                finishGame(completed = true)
            }
        }
    }

    private fun finishGame(completed: Boolean) {
        timer?.cancel()

        val i = Intent(this, ResultActivity::class.java)
        i.putExtra(ResultActivity.EXTRA_LEVEL, level.label)
        i.putExtra(ResultActivity.EXTRA_ATTEMPTS, if (::game.isInitialized) game.attempts else 0)
        i.putExtra(ResultActivity.EXTRA_ELAPSED, elapsedSeconds)
        i.putExtra(ResultActivity.EXTRA_COMPLETED, completed)
        startActivity(i)
        finish()
    }

    companion object {
        const val EXTRA_LEVEL = "extra_level"
    }
}
