package com.pauldelgado.neuropuzzles.data

enum class Level(val label: String) {
    EASY("Fácil"),
    MEDIUM("Medio"),
    HARD("Difícil")
}

data class CharacterItem(
    val id: String,
    val name: String,
    val imageUrl: String
)

data class Card(
    val pairId: String,
    val imageUrl: String,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)
