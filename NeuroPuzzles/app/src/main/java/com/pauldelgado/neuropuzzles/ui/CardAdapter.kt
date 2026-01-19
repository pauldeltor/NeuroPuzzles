package com.pauldelgado.neuropuzzles.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pauldelgado.neuropuzzles.R
import com.pauldelgado.neuropuzzles.data.Card

class CardAdapter(
    private val cards: MutableList<Card>,
    private val onCardClicked: (Int) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardVH(v)
    }

    override fun onBindViewHolder(holder: CardVH, position: Int) {
        holder.bind(cards[position])
        holder.itemView.setOnClickListener { onCardClicked(position) }
    }

    override fun getItemCount(): Int = cards.size

    fun notifyCardChanged(index: Int) {
        notifyItemChanged(index)
    }

    fun notifyCardsChanged(indices: List<Int>) {
        indices.forEach { notifyItemChanged(it) }
    }

    inner class CardVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iv: ImageView = itemView.findViewById(R.id.ivCard)

        fun bind(card: Card) {
            if (card.isFaceUp || card.isMatched) {
                iv.setBackgroundResource(0)
                Glide.with(iv)
                    .load(card.imageUrl)
                    .placeholder(R.drawable.card_back)
                    .error(R.drawable.card_back)
                    .into(iv)
                iv.alpha = if (card.isMatched) 0.5f else 1f
            } else {
                // Card face-down
                iv.alpha = 1f
                iv.setImageDrawable(null)
                iv.setBackgroundResource(R.drawable.card_back)
            }
        }
    }
}
