package com.vinarnt.jikan4j

import com.vinarnt.jikan4j.service.AnimeService
import com.vinarnt.jikan4j.service.SearchService


class RESTClient {

    val search = SearchService()
    val anime = AnimeService()
}