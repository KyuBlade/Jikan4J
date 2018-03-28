package com.vinarnt.jikan4j.service

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.vinarnt.jikan4j.Constants
import com.vinarnt.jikan4j.model.search.SearchType
import com.vinarnt.jikan4j.model.search.entry.*
import com.vinarnt.jikan4j.model.search.result.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.reflect.KClass


class SearchService : BaseService(Constants.API_SEARCH_PATH) {

    interface SearchCallback<T : SearchEntry> {

        fun onFailure(call: Call?, e: Exception?)

        fun onResponse(call: Call?, response: Response?, result: SearchResult<out SearchEntry>?)
    }

    interface AnimeSearchCallback : SearchCallback<AnimeSearchEntry>
    interface MangaSearchCallback : SearchCallback<MangaSearchEntry>
    interface PersonSearchCallback : SearchCallback<PersonSearchEntry>
    interface CharacterSearchCallback : SearchCallback<CharacterSearchEntry>

    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    /**
     * Search for animes from a keywords
     *
     * @param query keywords to search (Must be at least 3 characters long)
     * @param page result page to get (Default to 1)
     */
    fun searchAnimes(query: String, page: Int = 1): AnimeSearchResult? =
            search(query, page, SearchType.ANIME, AnimeSearchResult::class)

    /**
     * Search for mangas from a keywords
     *
     * @param query keywords to search (Must be at least 3 characters long)
     * @param page result page to get (Default to 1)
     */
    fun searchMangas(query: String, page: Int = 1): MangaSearchResult? =
            search(query, page, SearchType.MANGA, MangaSearchResult::class)

    /**
     * Search for persons from a keywords
     *
     * @param query keywords to search (Must be at least 3 characters long)
     * @param page result page to get (Default to 1)
     */
    fun searchPersons(query: String, page: Int = 1): PersonSearchResult? =
            search(query, page, SearchType.PERSON, PersonSearchResult::class)

    /**
     * Search for characters from a keywords
     *
     * @param query keywords to search (Must be at least 3 characters long)
     * @param page result page to get (Default to 1)
     */
    fun searchCharacters(query: String, page: Int = 1): CharacterSearchResult? =
            search(query, page, SearchType.CHARACTER, CharacterSearchResult::class)

    /**
     * Search for animes from a keywords asynchronously
     *
     * @param query keywords to search (Must be at least 3 characters long)
     * @param page result page to get (Default to 1)
     * @param callback result callback
     */
    fun asyncSearchAnimes(query: String, page: Int = 1, callback: AnimeSearchCallback) {

        asyncSearch(query, page, SearchType.ANIME, AnimeSearchResult::class, callback)
    }

    /**
     * Search for mangas from a keywords asynchronously
     *
     * @param query keywords to search (Must be at least 3 characters long)
     * @param page result page to get (Default to 1)
     * @param callback result callback
     */
    fun asyncSearchMangas(query: String, page: Int = 1, callback: MangaSearchCallback) {

        asyncSearch(query, page, SearchType.MANGA, MangaSearchResult::class, callback)
    }

    /**
     * Search for persons from a keywords asynchronously
     *
     * @param query keywords to search (Must be at least 3 characters long)
     * @param page result page to get (Default to 1)
     * @param callback result callback
     */
    fun asyncSearchPersons(query: String, page: Int = 1, callback: PersonSearchCallback) {

        asyncSearch(query, page, SearchType.PERSON, PersonSearchResult::class, callback)
    }

    /**
     * Search for characters from a keywords asynchronously
     *
     * @param query keywords to search (Must be at least 3 characters long)
     * @param page result page to get (Default to 1)
     * @param callback result callback
     */
    fun asyncSearchCharacters(query: String, page: Int = 1, callback: CharacterSearchCallback) {

        asyncSearch(query, page, SearchType.CHARACTER, CharacterSearchResult::class, callback)
    }

    /**
     * Generic synchronous search
     */
    private fun <T : SearchResult<*>> search(query: String, page: Int, searchType: SearchType, resultType: KClass<out T>): T? {

        if (query.length < 3)
            throw IllegalArgumentException("query parameter length must be higher than 3 characters")

        val url = getUrl(searchType.canonicalName, query, page)
        val response = client.newCall(Request.Builder().url(url).build()).execute()

        return normalizeResponse(response, resultType)
    }

    /**
     * Generic asynchronous search
     */
    private fun asyncSearch(query: String, page: Int, searchType: SearchType, resultType: KClass<out SearchResult<*>>, callback: SearchCallback<out SearchEntry>) {

        if (query.length < 3)
            throw IllegalArgumentException("query parameter length must be higher than 3 characters")

        val url = getUrl(searchType.canonicalName, query, page)

        client.newCall(Request.Builder().url(url).build()).enqueue(object : Callback {

            override fun onFailure(call: Call?, e: IOException?) {

                callback.onFailure(call, e)
            }

            override fun onResponse(call: Call?, response: Response) {

                try {
                    val result = normalizeResponse(response, resultType)

                    callback.onResponse(call, response, result)
                } catch (e: Exception) {

                    callback.onFailure(call, e)
                }
            }
        })
    }

    private fun <T : SearchResult<*>> normalizeResponse(response: Response, resultType: KClass<out T>): T? {

        return if (response.isSuccessful) {

            if (response.body() != null)
                moshi.adapter(resultType.java).fromJson(response.body()!!.string())
            else null

        } else null
    }
}
