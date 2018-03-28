package com.vinarnt.jikan4j.model.search.entry

import com.squareup.moshi.Json


data class MangaSearchEntry(

        override val id: Long,
        override val url: String,

        @Json(name = "image_url")
        override val imageUrl: String,

        val title: String,
        val description: String,
        val type: String,
        val score: Float,
        val volumes: Int,
        val members: Int

) : SearchEntry(id, url, imageUrl)