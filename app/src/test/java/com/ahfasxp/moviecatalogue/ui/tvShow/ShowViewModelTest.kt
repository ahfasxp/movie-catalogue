package com.ahfasxp.moviecatalogue.ui.tvShow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.ahfasxp.moviecatalogue.data.CatalogueRepository
import com.ahfasxp.moviecatalogue.utils.DataDummy
import com.ahfasxp.moviecatalogue.vo.Resource
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShowViewModelTest {
    private lateinit var showViewModel: ShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MainEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MainEntity>

    @Before
    fun setUp() {
        showViewModel = ShowViewModel(catalogueRepository)
    }

    @Test
    fun getTvshow() {
        val dummyShow = Resource.success(pagedList)
        `when`(dummyShow.data?.size).thenReturn(10)
        val shows = MutableLiveData<Resource<PagedList<MainEntity>>>()
        shows.value = dummyShow

        `when`(catalogueRepository.getAllShows()).thenReturn(shows)
        val showEntities = showViewModel.getTvshow().value?.data
        verify(catalogueRepository).getAllShows()
        assertNotNull(showEntities)
        assertEquals(10, showEntities?.size)

        showViewModel.getTvshow().observeForever(observer)
        verify(observer).onChanged(dummyShow)
    }
}