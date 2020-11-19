package com.ahfasxp.moviecatalogue.ui.favorite.favoriteMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ahfasxp.moviecatalogue.data.CatalogueRepository
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity

class FavoriteMovieViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getFavoriteMovie(): LiveData<PagedList<MainEntity>> {
        return catalogueRepository.getFavoriteMovie()
    }
}