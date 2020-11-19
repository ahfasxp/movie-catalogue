package com.ahfasxp.moviecatalogue.ui.movie

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
import com.ahfasxp.moviecatalogue.ui.detail.DetailActivity
import com.ahfasxp.moviecatalogue.ui.MainAdapter
import com.ahfasxp.moviecatalogue.viewmodel.ViewModelFactory
import com.ahfasxp.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_movie.*

/**
 * A simple [Fragment] subclass.
 * Use the [MovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            //ViewModel
            val factory = ViewModelFactory.getInstance(requireActivity())
            val movieViewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            //Menginisialisasi RecycleView dari MainAdapter
            val movieAdapter = MainAdapter()
            progressBar.visibility = View.VISIBLE
            movieViewModel.getMovies().observe(this, Observer { movies ->
                if (movies != null) {
                    when (movies.status) {
                        Status.LOADING -> progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            progressBar.visibility = View.GONE
                            movieAdapter.submitList(movies.data)
                            movieAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(rv_movie) {
                layoutManager = GridLayoutManager(activity, 2)
                adapter = movieAdapter
            }
            movieAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
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