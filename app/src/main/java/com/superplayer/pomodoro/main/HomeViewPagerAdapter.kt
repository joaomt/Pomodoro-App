package com.superplayer.pomodoro.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.superplayer.pomodoro.features.history.HistoryFragment
import com.superplayer.pomodoro.features.pomodoro.PomodoroFragment

class HomeViewPagerAdapter(private val tabTitles: List<String>, private val fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PomodoroFragment()
            1 -> HistoryFragment()
            else -> PomodoroFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }

    override fun getCount() = tabTitles.size
}