package dev.chu.memo.ui.permission

import android.os.Bundle
import dev.chu.memo.R
import dev.chu.memo.ui.permission.view.PermissionActivity

class Sample1PermissionActivity : PermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_permission)
        checkPermissionsAreGranted(BusinessRequest.PICK_PHOTO_FOR_FIRM_LOGO_FROM_GALLERY, true)
    }

    override fun onPermissionsGranted(businessRequest: BusinessRequest) {

    }

}