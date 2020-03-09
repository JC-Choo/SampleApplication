package dev.chu.memo.common

import android.Manifest

object Const {
    const val BASE_URL = "BASE_URL"

    const val TABLE_NAME_1 = "MEMO_DATA"
    const val TABLE_NAME_2 = "IMAGE_DATA"

    const val REQUEST_CODE_PERMISSIONS = 1000
    const val REQUEST_CODE_CAMERA_PERMISSION = 1001
    const val REQUEST_CODE_GALLERY_PERMISSION = 1002

    val usingPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION
    )

    object EXTRA {
        const val MEMO = "MEMO"

        const val MEMO_ID = "MEMO_ID"
    }

    object ARGS {
        const val MEMO_ID = "MEMO_ID"
    }
}