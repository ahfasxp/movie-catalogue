package com.ahfasxp.moviecatalogue.ui.favorite.favoriteMovie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ahfasxp.moviecatalogue.R
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.ahfasxp.moviecatalogue.ui.MainAdapter
import com.ahfasxp.moviecatalogue.ui.detail.DetailActivity
import com.ahfasxp.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.android.synthetic.main.fragment_movie.progressBar

class FavoriteMovieFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val favoriteMovieViewModel =
                ViewModelProvider(this, factory)[FavoriteMovieViewModel::class.java]

            //Menginisialisasi RecycleView dari MainAdapter
            val favoriteMovieAdapter = MainAdapter()
            progressBar.visibility = View.VISIBLE
            favoriteMovieViewModel.getFavoriteMovie().observe(this, Observer { movies ->
                progressBar.visibility = View.GONE
                favoriteMovieAdapter.submitList(movies)
                favoriteMovieAdapter.notifyDataSetChanged()
            })

            with(rv_favorite_movie) {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = favoriteMovieAdapter
            }
            favoriteMovieAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MainEntity) {
                    showSelectedMovie(data)
                }
            })
        }
    }

    //Metode item yang dipilih
    private fun showSelectedMovie(movie: MainEntity) {
        Toast.makeText(activity, "Kamu memilih ${movie.title}", Toast.LENGTH_SHORT).show()
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, movie.id)
        intent.putExtra(DetailActivity.EXTRA_TYPE, "movie")
        startActivity(intent)
    }
}