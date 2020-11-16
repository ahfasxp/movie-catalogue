package com.ahfasxp.moviecatalogue.data.source.remote

import android.os.Handler
import com.ahfasxp.moviecatalogue.data.source.remote.response.MainResponse
import com.ahfasxp.moviecatalogue.utils.JsonHelper

@Suppress("DEPRECATION")
class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {
    private val handler = Handler()

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllMovies(callback: LoadMoviesCallback) {
        handler.postDelayed(
            { callback.onAllCoursesReceived(jsonHelper.loadMovies()) },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    fun getAllShows(callback: LoadShowsCallback) {
        handler.postDelayed(
            { callback.onAllCoursesReceived(jsonHelper.loadShows()) },
            SERVICE_LATENCY_IN_MILLIS
        )
    }

    interface LoadMoviesCallback {
        fun onAllCoursesReceived(movieResponses: List<MainResponse>)
    }

    interface LoadShowsCallback {
        fun onAllCoursesReceived(showResponses: List<MainResponse>)
    }
}