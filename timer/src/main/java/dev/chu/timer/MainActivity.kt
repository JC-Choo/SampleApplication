package dev.chu.timer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dev.chu.timer.Utility.getFormattedStopWatch
import dev.chu.timer.databinding.ActivityMainBinding

/**
 * 참고 : https://medium.com/swlh/how-to-create-a-stopwatch-in-android-117912264491
 */
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val mInterval = TIMER_INTERVAL
    private var mHandler: Handler? = null
    private var timeInSeconds = 0L
    private var startButtonClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initStopWatch()

        binding!!.resetButton.setOnClickListener {
            stopTimer()
            resetTimerView()
        }
    }

    private fun initStopWatch() {
        binding?.textViewStopWatch?.text = getString(R.string.init_stop_watch_value)
    }

    private fun resetTimerView() {
        timeInSeconds = 0
        startButtonClicked = false
        binding?.startOrStopTextView?.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.teal_700
            )
        )
        binding?.startOrStopTextView?.setText(R.string.start)
        initStopWatch()
    }

    fun startOrStopButtonClicked(v: View) {
        if (!startButtonClicked) {
            startTimer()
            startTimerView()
        } else {
            stopTimer()
            stopTimerView()
        }
    }

    private fun stopTimerView() {
        binding?.startOrStopTextView?.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.teal_700
            )
        )
        binding?.startOrStopTextView?.setText(R.string.resume)
        startButtonClicked = false
    }

    private fun startTimerView() {
        binding?.startOrStopTextView?.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.red
            )
        )
        binding?.startOrStopTextView?.setText(R.string.stop)
        startButtonClicked = true
    }

    private fun startTimer() {
        mHandler = Handler(Looper.getMainLooper())
        mStatusChecker.run()
    }

    private fun stopTimer() {
        mHandler?.removeCallbacks(mStatusChecker)
    }

    private var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                timeInSeconds += 1
                Log.e("timeInSeconds", timeInSeconds.toString())
                updateStopWatchView(timeInSeconds)
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler!!.postDelayed(this, mInterval.toLong())
            }
        }
    }

    private fun updateStopWatchView(timeInSeconds: Long) {
        val formattedTime = getFormattedStopWatch((timeInSeconds * 1000))
        Log.e("formattedTime", formattedTime)
        binding?.textViewStopWatch?.text = formattedTime
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
        mHandler = null
    }
}