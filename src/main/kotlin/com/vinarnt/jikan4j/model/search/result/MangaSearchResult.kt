package com.vinarnt.jikan4j.model.search.result

import com.squareup.moshi.Json
import com.vinarnt.jikan4j.model.search.entry.MangaSearchEntry
import java.util.*


data class MangaSearchResult(

        override val result: Array<MangaSearchEntry>,

        @Json(name = "result_last_page")
        override val resultLastPage: Int

): SearchResult<MangaSearchEntry>(result, resultLastPage) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MangaSearchResult

        if (!Arrays.equals(result, other.result)) return false
        if (resultLastPage != other.resultLastPage) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = Arrays.hashCode(result)
        result1 = 31 * result1 + resultLastPage
        return result1
    }
}