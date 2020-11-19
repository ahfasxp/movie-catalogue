package com.ahfasxp.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.ahfasxp.moviecatalogue.vo.Resource

interface CatalogueDataSource {
    fun getAllMovies(): LiveData<Resource<PagedList<MainEntity>>>

    fun getAllShows(): LiveData<Resource<PagedList<MainEntity>>>

    fun getDetailMovie(id: String): LiveData<Resource<MainEntity>>

    fun getDetailShow(id: String): LiveData<Resource<MainEntity>>

    fun getFavoriteMovie(): LiveData<PagedList<MainEntity>>

    fun getFavoriteShow(): LiveData<PagedList<MainEntity>>

    fun setFavorite(main: MainEntity, state: Boolean)
}