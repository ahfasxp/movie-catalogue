package com.ahfasxp.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ahfasxp.moviecatalogue.data.CatalogueRepository
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
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
class DetailViewModelTest {
    private lateinit var detailViewModel: DetailViewModel
    private val dummyMovie = DataDummy.generateDummyMovie()[0]
    private val dummyShow = DataDummy.generateDummyTvshow()[0]
    private val id = "1"

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var catalogueRepository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<Resource<MainEntity>>

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(catalogueRepository)
        detailViewModel.setSelected("1")
    }

    @Test
    fun getMovie() {
        val dummyMovie = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<MainEntity>>()
        movie.value = dummyMovie

        `when`<LiveData<Resource<MainEntity>>>(catalogueRepository.getDetailMovie(id)).thenReturn(
            movie
        )

        detailViewModel.getMovie().observeForever(observer)

        verify(observer).onChanged(dummyMovie)
    }

    @Test
    fun getShow() {
        val dummyShow = Resource.success(dummyShow)
        val show = MutableLiveData<Resource<MainEntity>>()
        show.value = dummyShow

        `when`<LiveData<Resource<MainEntity>>>(catalogueRepository.getDetailShow(id)).thenReturn(
            show
        )

        detailViewModel.getShow().observeForever(observer)

        verify(observer).onChanged(dummyShow)
    }

}