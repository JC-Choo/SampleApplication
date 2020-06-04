package dev.chu.memo.ui.notification.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dev.chu.memo.R
import dev.chu.memo.databinding.FragmentEggTimerBinding

class EggTimerFragment : Fragment() {

    companion object {
        fun newInstance() = EggTimerFragment().apply {
            arguments = bundleOf()
        }
    }

//    val viewModel by activityViewModels<EggTimerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentEggTimerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_egg_timer, container, false
        )

        var viewModel = ViewModelProviders.of(this).get(EggTimerViewModel::class.java)
        viewModel = ViewModelProvider(this).get(EggTimerViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(EggTimerViewModel::class.java)

        binding.eggTimerViewModel = viewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        // TODO: Step 1.7 call create channel
        createChannel(
            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name)
        )

        return binding.root
    }

    private fun createChannel(channelId: String, channelName: String) {
        // TODO: Step 1.6 START create a channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                // TODO : Step 2.4 change importance
                NotificationManager.IMPORTANCE_LOW  // Notification의 중요 레벨 ( https://developer.android.com/reference/android/app/NotificationManager#IMPORTANCE_LOW )
            ).apply {
                // TODO: Step 2.6 disable badges for this channel
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Time for breakfast"
            }

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }
        // TODO: Step 1.6 END create channel
    }
}