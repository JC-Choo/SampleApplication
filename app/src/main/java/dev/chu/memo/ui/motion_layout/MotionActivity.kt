package dev.chu.memo.ui.motion_layout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.viewpager.widget.ViewPager
import dev.chu.memo.R
import kotlinx.android.synthetic.main.motion_23_view_pager.*

// https://medium.com/androiddevelopers/working-with-dynamic-data-in-motionlayout-9dbbcfe5ff75
// part 1, part2...

class MotionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_motion)
//        setContentView(R.layout.motion_04_image_filter)
//        setContentView(R.layout.motion_05_image_filter)
//        setContentView(R.layout.motion_06)
//        setContentView(R.layout.motion_09_coordinator_layout)
//        setContentView(R.layout.motion_13_drawer_layout)
        setContentView(R.layout.motion_23_view_pager)
        setViewPager()
    }

    private fun setViewPager() {
        val motionLayout = findViewById<MotionLayout>(R.id.motionLayout)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addPage("Page 1", R.layout.motion_16_viewpager_page1)
        adapter.addPage("Page 2", R.layout.motion_16_viewpager_page2)
        adapter.addPage("Page 3", R.layout.motion_16_viewpager_page3)
        pager.adapter = adapter
        tabs.setupWithViewPager(pager)
        if (motionLayout != null) {
            pager.addOnPageChangeListener(motionLayout as ViewPager.OnPageChangeListener)
        }

        val debugMode = if (intent.getBooleanExtra("showPaths", false)) {
            MotionLayout.DEBUG_SHOW_PATH
        } else {
            MotionLayout.DEBUG_SHOW_NONE
        }
        motionLayout.setDebugMode(debugMode)
    }
}