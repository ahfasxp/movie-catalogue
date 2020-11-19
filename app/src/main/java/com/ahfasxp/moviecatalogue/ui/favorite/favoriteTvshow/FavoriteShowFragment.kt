package com.ahfasxp.moviecatalogue.ui.favorite.favoriteTvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ahfasxp.moviecatalogue.R
import com.ahfasxp.moviecatalogue.data.source.local.entity.MainEntity
import com.ahfasxp.moviecatalogue.ui.MainAdapter
import com.ahfasxp.moviecatalogue.ui.detail.DetailActivity
import com.ahfasxp.moviecatalogue.ui.favorite.favoriteMovie.FavoriteMovieViewModel
import com.ahfasxp.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import kotlinx.android.synthetic.main.fragment_favorite_show.*
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.progressBar

class FavoriteShowFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val favoriteShowViewModel =
                ViewModelProvider(this, factory)[FavoriteShowViewModel::class.java]

            //Menginisialisasi RecycleView dari MainAdapter
            val favoriteShowAdapter = MainAdapter()
            progressBar.visibility = View.VISIBLE
            favoriteShowViewModel.getFavoriteShow().observe(this, Observer { shows ->
                progressBar.visibility = View.GONE
                favoriteShowAdapter.submitList(shows)
                favoriteShowAdapter.notifyDataSetChanged()
            })

            with(rv_favorite_show) {
                layoutManager = GridLayoutManager(activity, 2)
                setHasFixedSize(true)
                adapter = favoriteShowAdapter
            }
            favoriteShowAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MainEntity) {
                    showSelectedShow(data)
                }
            })
        }
    }

    //Metode item yang dipilih
    private fun showSelectedShow(show: MainEntity) {
        Toast.makeText(activity, "Kamu memilih ${show.title}", Toast.LENGTH_SHORT).show()
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_ID, show.id)
        intent.putExtra(DetailActivity.EXTRA_TYPE, "show")
        startActivity(intent)
    }
}