package dev.chu.memo.common

import android.Manifest

object Const {
    const val URL_CORONA = "https://8oi9s0nnth.apigw.ntruss.com/"
    const val URL_GITHUB = "https://api.github.com/"

    const val TOKEN = "76dca726488dff0477afcb5ed6d435453a5faf4f"

    const val TABLE_NAME_1 = "MEMO_DATA"
    const val TABLE_NAME_2 = "IMAGE_DATA"
    const val TABLE_NAME_3 = "repos"

    const val REQUEST_CODE_PERMISSIONS = 1000
    const val REQUEST_CODE_CAMERA_PERMISSION = 1001
    const val REQUEST_CODE_GALLERY_PERMISSION = 1002

    val usingPermissions = arrayOf(
//        Manifest.permission.CAMERA,
//        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    object PREF {
        const val MEMO_TITLE = "MEMO_TITLE"
        const val MEMO_CONTENT = "MEMO_CONTENT"
    }

    object EXTRA {
        const val MEMO_ID = "MEMO_ID"
        const val IS_WRITING_MEMO = "IS_WRITING_MEMO"
    }

    object ARGS {
        const val MEMO_ID = "MEMO_ID"
        const val COUNT = "COUNT"
    }
}