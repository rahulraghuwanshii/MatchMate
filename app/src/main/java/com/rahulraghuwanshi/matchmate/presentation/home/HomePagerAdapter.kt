package com.rahulraghuwanshi.matchmate.presentation.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rahulraghuwanshi.matchmate.presentation.explore.ExploreFragment
import com.rahulraghuwanshi.matchmate.presentation.profile_matches.ProfileMatchesFragment

class HomePagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_TABS = 1
    }

    override fun getItemCount() = NUM_TABS

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> ExploreFragment()

            1 -> ProfileMatchesFragment()

            else -> ExploreFragment()
        }
    }
}