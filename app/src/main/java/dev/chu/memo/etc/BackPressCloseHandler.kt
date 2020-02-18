package dev.chu.memo.etc

import android.app.Activity
import dev.chu.memo.R

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