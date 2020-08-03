package dev.chu.memo.ui.bottom

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityBottomNavigationBinding
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.etc.extension.replaceFragment

class BottomNavigationActivity: BaseActivity<ActivityBottomNavigationBinding>() {

    @LayoutRes
    override fun getLayoutRes(): Int = R.layout.activity_bottom_navigation

    private var count = 10

    override fun initView(savedInstanceState: Bundle?) {
        Log.i(TAG, "initView")

        replaceFragment(binding.bottomNavigationFl.id, HomeFragment.newInstance(count), HomeFragment.TAG)
        binding.bottomNavigationBnv.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(binding.bottomNavigationFl.id, HomeFragment.newInstance(count), HomeFragment.TAG)
                    removeBadge()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_notifications -> {
                    addBadge(++count)
                    replaceFragment(binding.bottomNavigationFl.id, NotificationFragment.newInstance(count), NotificationFragment.TAG)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_profile -> {
                    replaceFragment(binding.bottomNavigationFl.id, ProfileFragment.newInstance(count), ProfileFragment.TAG)
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        })
    }

    private fun addBadge(count : Int) {
        val badge: BadgeDrawable = binding.bottomNavigationBnv.getOrCreateBadge(R.id.navigation_notifications)
        badge.number = count
        badge.isVisible = true
    }

    private fun removeBadge() {
        binding.bottomNavigationBnv.removeBadge(R.id.navigation_notifications)
        count = 5
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finish()
    }
}