package com.ahfasxp.moviecatalogue.ui.favorite.favoriteTvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ahfasxp.moviecatalogue.data.CatalogueRepository
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity

class FavoriteShowViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getFavoriteShow(): LiveData<PagedList<MainEntity>> {
        return catalogueRepository.getFavoriteShow()
    }
}