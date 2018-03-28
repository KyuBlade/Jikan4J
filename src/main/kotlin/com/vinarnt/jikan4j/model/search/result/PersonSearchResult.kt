package com.vinarnt.jikan4j.model.search.result

import com.squareup.moshi.Json
import com.vinarnt.jikan4j.model.search.entry.PersonSearchEntry
import java.util.*


data class PersonSearchResult(

        override val result: Array<PersonSearchEntry>,

        @Json(name = "result_last_page")
        override val resultLastPage: Int

): SearchResult<PersonSearchEntry>(result, resultLastPage) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonSearchResult

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