package com.superplayer.pomodoro.features.history

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.superplayer.pomodoro.R
import com.superplayer.pomodoro.common.adapter.AdapterGeneric
import com.superplayer.pomodoro.common.adapter.ViewHolderFactory
import com.superplayer.pomodoro.common.extensions.observeValue
import com.superplayer.pomodoro.common.sync.BroadcastPomodoro
import com.superplayer.pomodoro.databinding.FragmentHistoryBinding
import com.superplayer.pomodoro.features.history.viewState.HistoryViewState
import com.superplayer.pomodoro.features.history.viewState.RowContentHistoryViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private val historyViewModel by viewModels<HistoryViewModel>()
    private var viewBinding: FragmentHistoryBinding? = null
    private lateinit var adapterHistory: AdapterGeneric<ViewHolderFactory>

    private val pomodoroReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            historyViewModel.fetchListHistory()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)

        setupViewBinding()
        initComponents()
        setupObservers()

        return viewBinding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        registerReceiverPomodoro()
    }

    override fun onResume() {
        super.onResume()
        registerReceiverPomodoro()
        historyViewModel.fetchListHistory()

    }

    override fun onPause() {
        super.onPause()
        try {
            activity?.unregisterReceiver(pomodoroReceiver)
        }catch (ex : Exception){
            ex.printStackTrace()
        }
    }

    private fun registerReceiverPomodoro() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BroadcastPomodoro.ACTION_RECEIVER_STATE)
        activity?.registerReceiver(pomodoroReceiver, intentFilter)
    }

    private fun setupViewBinding() {
        viewBinding?.apply {
            lifecycleOwner = this@HistoryFragment
        }
    }

    private fun initComponents() {
        configListHistory()
    }

    private fun setupObservers() {
        historyViewModel.historyViewState.observeValue(viewLifecycleOwner, ::renderViewState)
    }

    private fun renderViewState(historyViewState: HistoryViewState) {
        viewBinding?.viewState = historyViewState
        adapterHistory.submitList(historyViewState.listHistory)
    }

    private fun configListHistory() {
        adapterHistory = AdapterGeneric()
        viewBinding?.rcHistory?.apply {
            adapter = adapterHistory
        }
    }

}