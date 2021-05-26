package com.superplayer.pomodoro.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.superplayer.pomodoro.R
import com.superplayer.pomodoro.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var viewBinding: ActivityMainBinding? = null

    private lateinit var homeViewPagerAdapter: HomeViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initComponents()
    }

    private fun initComponents() {
        configViewPager()
    }

    private fun configViewPager() {
        val tabsTitle =
            listOf(getString(R.string.menu_item_new), getString(R.string.menu_item_history))
        homeViewPagerAdapter = HomeViewPagerAdapter(tabsTitle, supportFragmentManager)

        viewBinding?.apply {
            viewPagerMain.adapter = homeViewPagerAdapter
            tabViewPagerMain.setupWithViewPager(viewPagerMain)
        }
    }

}
