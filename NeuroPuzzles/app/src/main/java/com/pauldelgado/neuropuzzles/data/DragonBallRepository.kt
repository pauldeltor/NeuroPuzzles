package com.pauldelgado.neuropuzzles.data

import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class DragonBallRepository {

    private val client = OkHttpClient()

    /**
     * Fetches characters from the Dragon Ball API.
     * We keep parsing defensive because the API may return either
     * an object with "items" or a raw JSON array.
     */
    fun fetchCharacters(limit: Int): List<CharacterItem> {
        val url = "https://dragonball-api.com/api/characters?limit=$limit"
        val request = Request.Builder().url(url).get().build()

        client.newCall(request).execute().use { resp ->
            if (!resp.isSuccessful) {
                throw IllegalStateException("HTTP ${resp.code}")
            }
            val body = resp.body?.string().orEmpty()
            return parseCharacters(body)
        }
    }

    private fun parseCharacters(json: String): List<CharacterItem> {
        val trimmed = json.trim()
        val arr = when {
            trimmed.startsWith("[") -> JSONArray(trimmed)
            else -> {
                val obj = JSONObject(trimmed)
                when {
                    obj.has("items") -> obj.getJSONArray("items")
                    obj.has("data") -> obj.getJSONArray("data")
                    else -> JSONArray()
                }
            }
        }

        val out = mutableListOf<CharacterItem>()
        for (i in 0 until arr.length()) {
            val o = arr.optJSONObject(i) ?: continue
            val id = o.optString("id").ifBlank { o.optString("_id") }.ifBlank { i.toString() }
            val name = o.optString("name", "Unknown")
            val image = o.optString("image").ifBlank { o.optString("imageUrl") }.ifBlank { o.optString("avatar") }
            if (image.isNotBlank()) {
                out += CharacterItem(id = id, name = name, imageUrl = image)
            }
        }
        return out
    }
}
