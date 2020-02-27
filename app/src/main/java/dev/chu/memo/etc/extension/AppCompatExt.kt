package dev.chu.memo.etc.extension

import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat


fun AppCompatActivity.setActionBarHome(toolbar: Toolbar, @DrawableRes res: Int? = null) {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        res?.let {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setHomeAsUpIndicator(it)
        } ?: run {
            setDisplayHomeAsUpEnabled(false)
            setHomeButtonEnabled(false)
            setHomeAsUpIndicator(null)
        }
    }
}

fun AppCompatActivity.hideActionBar() {
    supportActionBar?.hide()
}

fun AppCompatActivity.checkUsingPermission(permissions: Array<String>, requestCode: Int) {
    if(isPermissionsVersion()) {
        var isReCallPermission = false
        for(i in permissions.indices) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                isReCallPermission = true
                break
            } else {
                alertDialog("퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ")
            }
        }

        if(isReCallPermission)
            requestPermissions(permissions, requestCode)
    }
}

// vararg : 가변인자 : 갯수가 정해지지 않는 인자로 인자의 개수를 유동적으로 받을 수 있습니다.
fun AppCompatActivity.hasPermissions(vararg permissions: String): Boolean =
    permissions.all {
        ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

fun isPermissionsVersion(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M