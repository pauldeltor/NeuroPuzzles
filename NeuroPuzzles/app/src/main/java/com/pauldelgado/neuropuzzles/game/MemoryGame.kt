package com.pauldelgado.neuropuzzles.game

import com.pauldelgado.neuropuzzles.data.Card

class MemoryGame(private val cards: List<Card>) { // Changed to List as we shouldn't mutate the external reference directly

    private var firstIndex: Int? = null
    var attempts: Int = 0
        private set

    // Return a copy or an immutable list to prevent external modification of game state
    fun getCards(): List<Card> = cards

    fun canFlip(index: Int): Boolean {
        // Added bounds check to prevent IndexOutOfBoundsException
        if (index !in cards.indices) return false
        val card = cards[index]

        // Prevent flipping if two cards are already being compared (waiting for hide)
        // or if the card is already face up/matched.
        return !card.isMatched && !card.isFaceUp
    }

    /**
     * Flips a card. Returns a MatchResult:
     * - if second card was flipped, returns whether it's a match and the indices involved.
     * - if it's the first flip, returns null.
     */
    fun flip(index: Int): MatchResult? {
        if (!canFlip(index)) return null

        val card = cards[index]
        card.isFaceUp = true

        val fi = firstIndex
        return if (fi == null) {
            firstIndex = index
            null
        } else {
            // Check if user clicked the exact same card twice (if logic allows)
            if (fi == index) return null

            attempts += 1
            val firstCard = cards[fi]
            val secondCard = cards[index]

            val isMatch = firstCard.pairId == secondCard.pairId

            if (isMatch) {
                firstCard.isMatched = true
                secondCard.isMatched = true
            }

            // Reset firstIndex before returning result
            firstIndex = null
            MatchResult(first = fi, second = index, isMatch = isMatch)
        }
    }

    /**
     * Hides specific indices.
     * Added safety: If a card is already matched, it should NEVER be hidden.
     */
    fun hide(indices: List<Int>) {
        indices.forEach { i ->
            if (i in cards.indices) {
                val card = cards[i]
                if (!card.isMatched) {
                    card.isFaceUp = false
                }
            }
        }
    }

    fun allMatched(): Boolean = cards.all { it.isMatched }

    data class MatchResult(val first: Int, val second: Int, val isMatch: Boolean)
}