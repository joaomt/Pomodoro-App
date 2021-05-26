package com.superplayer.pomodoro.features.pomodoro

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.superplayer.pomodoro.R
import com.superplayer.pomodoro.common.extensions.observeValue
import com.superplayer.pomodoro.common.extensions.playSoundPhone
import com.superplayer.pomodoro.common.extensions.pxTodp
import com.superplayer.pomodoro.common.extensions.vibratePhone
import com.superplayer.pomodoro.common.sync.BroadcastPomodoro
import com.superplayer.pomodoro.common.sync.Notification
import com.superplayer.pomodoro.databinding.FragmentPomodoroBinding
import com.superplayer.pomodoro.features.pomodoro.viewState.PomodoroViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PomodoroFragment : Fragment() {
    private val pomodoroViewModel by viewModels<PomodoroViewModel>()
    private var viewBinding: FragmentPomodoroBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pomodoro, container, false)

        setupViewBinding()
        setupObservers()

        return viewBinding?.root
    }

    private fun setupViewBinding() {
        viewBinding?.let {
            it.lifecycleOwner = this@PomodoroFragment
            it.viewModel = pomodoroViewModel
        }
    }

    private fun setupObservers() {
        pomodoroViewModel.pomodoroViewState.observeValue(viewLifecycleOwner, ::renderViewState)
        pomodoroViewModel.pomodoroFinished.observeValue(
            viewLifecycleOwner,
            ::showAlertsUser
        )
        pomodoroViewModel.sendBroadcast.observeValue(
            viewLifecycleOwner,
            ::dispatchAlertsPomodoroFinished
        )
        pomodoroViewModel.createAlarmManager.observeValue(
            viewLifecycleOwner,
            ::createAlarmManager
        )
    }

    private fun renderViewState(pomodoroViewState: PomodoroViewState) {
        viewBinding?.viewState = pomodoroViewState
    }

    private fun dispatchAlertsPomodoroFinished(action: String) {
        activity?.sendBroadcast(Intent(action))
    }

    private fun createAlarmManager(idPomodoro: Long) {
        BroadcastPomodoro.createAlarmManager(requireContext(), idPomodoro)
    }

    private fun showAlertsUser(showAlerts: Boolean) {
        if (showAlerts) {
            val title = getString(R.string.notification_title)
            Notification.show(requireContext(), title, withSound = false)

            showDialogPomodoroFinished()
            activity?.vibratePhone()
            activity?.playSoundPhone()
        }
    }

    private fun showDialogPomodoroFinished() {
        val alertBuilder = AlertDialog.Builder(requireContext(), R.style.CustomDialogTransParent)
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.alert_dialog_pomodoro_finished, null)
        alertBuilder.setView(dialogView)

        alertBuilder.create().apply {
            try {
                show()
                window?.setLayout(pxTodp(250), pxTodp(250))
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }
}