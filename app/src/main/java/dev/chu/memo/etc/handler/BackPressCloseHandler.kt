package dev.chu.memo.etc.handler

import android.app.Activity
import dev.chu.memo.R
import dev.chu.memo.etc.extension.showToast

class BackPressCloseHandler(private val context: Activity) {
    private var backKeyPressedTime = 0L

    fun onBackPressed() {
        if(System.currentTimeMillis() > backKeyPressedTime + 2000) {
            context.showToast(R.string.back_pressed_message)
            backKeyPressedTime = System.currentTimeMillis()
            return
        }

        if(System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            context.finish()
        }
    }
}