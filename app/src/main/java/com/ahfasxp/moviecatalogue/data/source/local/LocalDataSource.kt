package com.ahfasxp.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.ahfasxp.moviecatalogue.data.source.local.room.CatalogueDao

class LocalDataSource private constructor(private val mCatalogueDao: CatalogueDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(catalogueDao)
    }

    fun getAllMovies(): DataSource.Factory<Int, MainEntity> = mCatalogueDao.getMovies()

    fun getAllShows(): DataSource.Factory<Int, MainEntity> = mCatalogueDao.getShows()

    fun getDetailMovie(id: String): LiveData<MainEntity> = mCatalogueDao.getDetailMovie(id)

    fun getDetailShow(id: String): LiveData<MainEntity> = mCatalogueDao.getDetailShow(id)

    fun getFavoriteMovie(): DataSource.Factory<Int, MainEntity> = mCatalogueDao.getFavoriteMovie()

    fun getFavoriteShow(): DataSource.Factory<Int, MainEntity> = mCatalogueDao.getFavoriteShow()

    fun insertCatalogue(main: List<MainEntity>) = mCatalogueDao.insertCatalogue(main)

    fun setFavorite(main: MainEntity, newState: Boolean) {
        main.isFavorite = newState
        mCatalogueDao.updateCatalogue(main)
    }
}