package com.ahfasxp.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ahfasxp.moviecatalogue.data.source.local.LocalDataSource
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.ahfasxp.moviecatalogue.data.source.remote.RemoteDataSource
import com.ahfasxp.moviecatalogue.utils.AppExecutors
import com.ahfasxp.moviecatalogue.utils.DataDummy
import com.ahfasxp.moviecatalogue.utils.DataDummy.generateDummyMovie
import com.ahfasxp.moviecatalogue.utils.LiveDataTestUtil
import com.ahfasxp.moviecatalogue.utils.PagedListUtil
import com.ahfasxp.moviecatalogue.vo.Resource
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import com.nhaarman.mockitokotlin2.any
import org.mockito.Mockito.*

class CatalogueRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val catalogueRepository = FakeCatalogueRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovie()
    private val showResponses = DataDummy.generateRemoteDummyTvshow()
    private val id = "1"

    @Test
    fun getAllMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MainEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        catalogueRepository.getAllMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(generateDummyMovie()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllShows() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MainEntity>
        `when`(local.getAllShows()).thenReturn(dataSourceFactory)
        catalogueRepository.getAllShows()

        val showEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvshow()))
        verify(local).getAllShows()
        assertNotNull(showEntities.data)
        assertEquals(showResponses.size.toLong(), showEntities.data?.size?.toLong())
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = DataDummy.generateDummyMovie()[0]
        val movie = MutableLiveData<MainEntity>()
        movie.value = dummyMovie
        `when`<LiveData<MainEntity>>(local.getDetailMovie(id)).thenReturn(movie)

        val mainEntities =
            LiveDataTestUtil.getValue(catalogueRepository.getDetailMovie(id))
        verify(local).getDetailMovie(id)
        assertNotNull(mainEntities.data)
        assertNotNull(mainEntities.data?.title)
        assertEquals(movieResponses[0].title, mainEntities.data?.title)
    }

    @Test
    fun getDetailShow() {
        val dummyShow = DataDummy.generateDummyTvshow()[0]
        val show = MutableLiveData<MainEntity>()
        show.value = dummyShow
        `when`<LiveData<MainEntity>>(local.getDetailShow(id)).thenReturn(show)

        val mainEntities =
            LiveDataTestUtil.getValue(catalogueRepository.getDetailShow(id))
        verify(local).getDetailShow(id)
        assertNotNull(mainEntities.data)
        assertNotNull(mainEntities.data?.title)
        assertEquals(showResponses[0].title, mainEntities.data?.title)
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MainEntity>
        `when`(local.getFavoriteMovie()).thenReturn(dataSourceFactory)
        catalogueRepository.getFavoriteMovie()

        val mainEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateRemoteDummyMovie()))
        verify(local).getFavoriteMovie()
        assertNotNull(mainEntities)
        assertEquals(movieResponses.size.toLong(), mainEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteShow() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MainEntity>
        `when`(local.getFavoriteShow()).thenReturn(dataSourceFactory)
        catalogueRepository.getFavoriteShow()

        val mainEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvshow()))
        verify(local).getFavoriteShow()
        assertNotNull(mainEntities)
        assertEquals(showResponses.size.toLong(), mainEntities.data?.size?.toLong())
    }
}