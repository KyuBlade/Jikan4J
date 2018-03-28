package com.vinarnt.jikan4j.model.search.entry

import com.squareup.moshi.Json
import com.vinarnt.jikan4j.model.anime.AnimeReference
import com.vinarnt.jikan4j.model.manga.MangaReference
import java.util.*


data class CharacterSearchEntry(

        @Transient override val id: Long = 0,
        override val url: String,

        @Json(name = "image_url")
        override val imageUrl: String,

        val name: String,
        val nicknames: String?,
        val animes: Array<AnimeReference> = arrayOf(),
        val mangas: Array<MangaReference> = arrayOf()

) : SearchEntry(id, url, imageUrl) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharacterSearchEntry

        if (id != other.id) return false
        if (url != other.url) return false
        if (imageUrl != other.imageUrl) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + name.hashCode()
        nicknames?.let { result = 31 * result + it.hashCode() }
        result = 31 * result + Arrays.hashCode(animes)
        result = 31 * result + Arrays.hashCode(mangas)
        return result
    }
}