package dev.chu.memo.etc.handler

import androidx.appcompat.app.AppCompatActivity
import dev.chu.memo.R
import dev.chu.memo.etc.extension.showToast

class BackPressCloseHandler(private val activity: AppCompatActivity) {
    private var backKeyPressedTime = 0L

    fun onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
            activity.showToast(R.string.back_pressed_message)
            backKeyPressedTime = System.currentTimeMillis()
            return
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish()
        }
    }
}