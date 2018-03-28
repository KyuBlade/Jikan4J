package com.vinarnt.jikan4j

import com.vinarnt.jikan4j.model.search.entry.SearchEntry
import com.vinarnt.jikan4j.model.search.result.SearchResult
import com.vinarnt.jikan4j.service.SearchService
import okhttp3.Call
import okhttp3.Response
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


class SearchTests: BaseRESTTest() {

    @Before
    fun beforeEach() {

        // Wait 5 seconds between each request
        Thread.sleep(5000L)
    }

    @Test
    fun searchAnimes() {

        val result = RESTClient().search.searchAnimes("code geass")

        Assert.assertTrue("Result is null", result != null)
        Assert.assertTrue("Result is empty", result!!.result.isNotEmpty())

        println("Result contains ${result.result.size} entries")
    }

    @Test
    fun searchMangas() {

        val result = RESTClient().search.searchMangas("naruto")

        Assert.assertTrue("Result is null", result != null)
        Assert.assertTrue("Result is empty", result!!.result.isNotEmpty())

        println("Result contains ${result.result.size} entries")
    }

    @Test
    fun searchPersons() {

        val result = RESTClient().search.searchPersons("kishimoto")

        Assert.assertTrue("Result is null", result != null)
        Assert.assertTrue("Result is empty", result!!.result.isNotEmpty())

        println("Result contains ${result.result.size} entries")
    }

    @Test
    fun searchCharacters() {

        val result = RESTClient().search.searchCharacters("ryo saeba")

        Assert.assertTrue("Result is null", result != null)
        Assert.assertTrue("Result is empty", result!!.result.isNotEmpty())

        println("Result contains ${result.result.size} entries")
    }

    @Test
    fun asyncSearchAnimes() {

        val countDownLatch = CountDownLatch(1)

        RESTClient().search.asyncSearchAnimes("code geass", callback = object: SearchService.AnimeSearchCallback {

            override fun onFailure(call: Call?, e: Exception?) {

                Assert.fail("Request failed to be sent")
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call?, response: Response?, result: SearchResult<out SearchEntry>?) {

                Assert.assertNotNull("Response must not be null", response)
                Assert.assertTrue("Response error", response!!.isSuccessful)
                Assert.assertTrue("Result is null", result != null)
                Assert.assertTrue("Result is empty", result!!.result.isNotEmpty())

                println("Result contains ${result.result.size} entries")

                countDownLatch.countDown()
            }
        })

        countDownLatch.await(10L, TimeUnit.SECONDS)
    }

    @Test
    fun asyncSearchMangas() {

        val countDownLatch = CountDownLatch(1)

        RESTClient().search.asyncSearchMangas("naruto", callback = object: SearchService.MangaSearchCallback {

            override fun onFailure(call: Call?, e: Exception?) {

                Assert.fail("Request failed to be sent")
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call?, response: Response?, result: SearchResult<out SearchEntry>?) {

                Assert.assertNotNull("Response must not be null", response)
                Assert.assertTrue("Response error", response!!.isSuccessful)
                Assert.assertTrue("Result is null", result != null)
                Assert.assertTrue("Result is empty", result!!.result.isNotEmpty())

                println("Result contains ${result.result.size} entries")

                countDownLatch.countDown()
            }
        })

        countDownLatch.await(10L, TimeUnit.SECONDS)
    }

    @Test
    fun asyncSearchPersons() {

        val countDownLatch = CountDownLatch(1)

        RESTClient().search.asyncSearchPersons("kishimoto", callback = object: SearchService.PersonSearchCallback {

            override fun onFailure(call: Call?, e: Exception?) {

                countDownLatch.countDown()
                Assert.fail("Request failed to be sent: ${e?.message}")
            }

            override fun onResponse(call: Call?, response: Response?, result: SearchResult<out SearchEntry>?) {

                Assert.assertNotNull("Response must not be null", response)
                Assert.assertTrue("Response error", response!!.isSuccessful)
                Assert.assertTrue("Result is null", result != null)
                Assert.assertTrue("Result is empty", result!!.result.isNotEmpty())

                println("Result contains ${result.result.size} entries")

                countDownLatch.countDown()
            }
        })

        countDownLatch.await(10L, TimeUnit.SECONDS)
    }

    @Test
    fun asyncSearchCharacters() {

        val countDownLatch = CountDownLatch(1)

        RESTClient().search.asyncSearchCharacters("ryo saeba", callback = object: SearchService.CharacterSearchCallback {

            override fun onFailure(call: Call?, e: Exception?) {

                countDownLatch.countDown()
                Assert.fail("Request failed to be sent: ${e?.message}")
            }

            override fun onResponse(call: Call?, response: Response?, result: SearchResult<out SearchEntry>?) {

                Assert.assertNotNull("Response must not be null", response)
                Assert.assertTrue("Response error", response!!.isSuccessful)
                Assert.assertTrue("Result is null", result != null)
                Assert.assertTrue("Result is empty", result!!.result.isNotEmpty())

                println("Result contains ${result.result.size} entries")

                countDownLatch.countDown()
            }
        })

        countDownLatch.await(10L, TimeUnit.SECONDS)
    }
}