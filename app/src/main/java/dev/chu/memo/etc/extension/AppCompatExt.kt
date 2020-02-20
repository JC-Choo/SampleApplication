package dev.chu.memo.etc.extension

import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

fun AppCompatActivity.setActionBarHome(toolbar: Toolbar, @DrawableRes res: Int) {
    setSupportActionBar(toolbar)
    supportActionBar?.let {
        it.setDisplayHomeAsUpEnabled(true)
        it.setHomeButtonEnabled(true)
        it.setHomeAsUpIndicator(res)
    }
}

fun AppCompatActivity.hideActionBar() {
    supportActionBar?.hide()
}