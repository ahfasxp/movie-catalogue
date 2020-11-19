package com.ahfasxp.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity

@Dao
interface CatalogueDao {
    @Query("SELECT * FROM mainentities WHERE type = 'movie'")
    fun getMovies(): DataSource.Factory<Int, MainEntity>

    @Query("SELECT * FROM mainentities WHERE type = 'show'")
    fun getShows(): DataSource.Factory<Int, MainEntity>

    @Query("SELECT * FROM mainentities WHERE id = :id AND type = 'movie'")
    fun getDetailMovie(id: String): LiveData<MainEntity>

    @Query("SELECT * FROM mainentities WHERE id = :id AND type = 'show'")
    fun getDetailShow(id: String): LiveData<MainEntity>

    @Query("SELECT * FROM mainentities where isFavorite = 1 AND type = 'movie'")
    fun getFavoriteMovie(): DataSource.Factory<Int, MainEntity>

    @Query("SELECT * FROM mainentities where isFavorite = 1 AND type = 'show'")
    fun getFavoriteShow(): DataSource.Factory<Int, MainEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCatalogue(catalogue: List<MainEntity>)

    @Update
    fun updateCatalogue(catalogue: MainEntity)
}