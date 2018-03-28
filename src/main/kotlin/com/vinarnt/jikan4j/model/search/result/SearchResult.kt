package com.vinarnt.jikan4j.model.search.result

import com.vinarnt.jikan4j.model.search.entry.SearchEntry


abstract class SearchResult<T: SearchEntry>(open val result: Array<T>, open val resultLastPage: Int)