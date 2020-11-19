package com.ahfasxp.moviecatalogue.ui.favorite.favoriteTvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ahfasxp.moviecatalogue.data.CatalogueRepository
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.ahfasxp.moviecatalogue.ui.favorite.favoriteMovie.FavoriteMovieViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteShowViewModelTest {
    private lateinit var viewModel: FavoriteShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MainEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MainEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteShowViewModel(catalogueRepository)
    }

    @Test
    fun getFavoriteMovie() {
        val dummyShows = pagedList
        `when`(dummyShows.size).thenReturn(10)
        val shows = MutableLiveData<PagedList<MainEntity>>()
        shows.value = dummyShows

        `when`(catalogueRepository.getFavoriteShow()).thenReturn(shows)
        val showEntities = viewModel.getFavoriteShow().value
        verify<CatalogueRepository>(catalogueRepository).getFavoriteShow()
        assertNotNull(showEntities)
        assertEquals(10, showEntities?.size)

        viewModel.getFavoriteShow().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyShows)
    }
}