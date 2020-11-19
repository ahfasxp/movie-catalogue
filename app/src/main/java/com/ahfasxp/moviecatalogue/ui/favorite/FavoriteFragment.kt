package com.ahfasxp.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahfasxp.moviecatalogue.R
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            //Setup ViewPager
            val sectionsPagerAdapter =
                SectionsPagerAdapter(this, childFragmentManager)
            vp_favorite.adapter = sectionsPagerAdapter
            tabs_favorite.setupWithViewPager(vp_favorite)
        }
    }
}