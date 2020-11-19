package com.ahfasxp.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.ahfasxp.moviecatalogue.data.source.local.room.CatalogueDao

class LocalDataSource private constructor(private val mCatalogueDao: CatalogueDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(catalogueDao)
    }

    fun getAllMovies(): LiveData<List<MainEntity>> = mCatalogueDao.getMovies()

    fun getAllShows(): LiveData<List<MainEntity>> = mCatalogueDao.getShows()

    fun getDetailMovie(id: String): LiveData<MainEntity> = mCatalogueDao.getDetailMovie(id)

    fun getDetailShow(id: String): LiveData<MainEntity> = mCatalogueDao.getDetailShow(id)

    fun getFavoriteMovie(): LiveData<List<MainEntity>> = mCatalogueDao.getFavoriteMovie()

    fun getFavoriteShow(): LiveData<List<MainEntity>> = mCatalogueDao.getFavoriteShow()

    fun insertCatalogue(main: List<MainEntity>) = mCatalogueDao.insertCatalogue(main)

    fun setFavorite(main: MainEntity, newState: Boolean) {
        main.isFavorite = newState
        mCatalogueDao.updateCatalogue(main)
    }
}