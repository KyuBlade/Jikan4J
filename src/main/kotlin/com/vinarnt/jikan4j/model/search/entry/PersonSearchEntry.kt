package com.vinarnt.jikan4j.model.search.entry

import com.squareup.moshi.Json


data class PersonSearchEntry(

        override val id: Long,
        override val url: String,

        @Json(name = "image_url")
        override val imageUrl: String,

        val name: String,
        val nicknames: String?

) : SearchEntry(id, url, imageUrl)