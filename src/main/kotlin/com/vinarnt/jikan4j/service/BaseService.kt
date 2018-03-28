package com.vinarnt.jikan4j.service

import com.vinarnt.jikan4j.Constants
import okhttp3.HttpUrl
import okhttp3.OkHttpClient


open class BaseService(private val path: String) {

    companion object {

        val client = OkHttpClient()
    }

    protected fun getUrl(vararg pathSegments: Any) = HttpUrl.Builder()
            .scheme(Constants.API_SCHEME)
            .host(Constants.API_HOST)
            .addPathSegment(path)
            .apply { pathSegments.forEach { addPathSegment(it.toString()) } }
            .build()
}