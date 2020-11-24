package dev.chu.memo.ui.permission.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import dev.chu.memo.ui.permission.BusinessRequest
import dev.chu.memo.ui.permission.PermissionListener
import dev.chu.memo.ui.permission.PermissionManager

/**
 * Created by Egemen Hamutçu on 2020-01-02.
 *
 * # A Primary Class For Permission Works
 * ## Usage:
 * Implement this fragment if your fragment requires Android permission. You have to only call
 *
 * [checkPermissionsAreGranted] method with a desired [BusinessRequest] and process your work in
 *
 * [onPermissionsGranted] method in case of user grant for permissions.
 * ##
 * ## **NEVER** throw a dialog to the user in your fragment!
 *
 * If user denies any permissions, this fragment will throw a necessary dialog for you, so you
 *
 * must **NOT** try to throw a dialog to the user. This fragment will take necessary actions
 *
 * according to user choices. You can optionally override [onPermissionsDenied] method if you need
 *
 * to do extra work in case of user denial for permissions.
 * ##
 * ## Are you confused which [BusinessRequest] should you use?
 * You may need to add additional enum value in [BusinessRequest] according to your business needs.
 *
 * In that case, you must also add necessary string resources for rationale & permanent denial
 *
 * messages & titles in **strings_permission.xml**. You must also add additional cases for brand new
 *
 * [BusinessRequest] which will be used in switch-case operations in [PermissionManager]. If your
 *
 * [BusinessRequest] requires additional permissions for our application, you must also add it as
 *
 * enum value in [PermissionRequest] and handle it in switch-case operations in both
 *
 * [PermissionRequest.getPermissionTextArray] & [PermissionRequest.getPermissionRequestFromText]
 *
 * methods.
 *
 * @see [PermissionManager]
 *
 * @author Egemen Hamutçu
 */
abstract class PermissionFragment : Fragment() {

    /**
     * This method is invoked when all permissions are granted given in [BusinessRequest]
     * of [checkPermissionsAreGranted] method.
     *
     * @param businessRequest provided by [BusinessRequest] given in [checkPermissionsAreGranted] method
     *
     * @author Egemen Hamutçu
     */
    abstract fun onPermissionsGranted(businessRequest: BusinessRequest)

    private lateinit var permissionManager: PermissionManager
    private lateinit var businessRequest: BusinessRequest
    private var isComingFromSettings = false
    private var exitOnDenied = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permissionListener = object :
            PermissionListener {
            override fun onNativePermissionRequestReady(permissions: Array<String>, requestCode: Int) {
                requestPermissions(permissions, requestCode)
            }

            override fun grantPermissions(businessRequest: BusinessRequest) {
                onPermissionsGranted(businessRequest)
            }

            override fun navigateToSettings() {
                activity?.let {
                    isComingFromSettings = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", it.packageName, null))
                    startActivity(intent)
                }
            }

            override fun deny() {
                onPermissionsDenied()
            }

            override fun retry() {
                checkPermissionsAreGranted(businessRequest, exitOnDenied)
            }
        }

        permissionManager =
            PermissionManager(permissionListener)
    }

    /**
     * Checks whether user granted necessary permissions for [businessRequest]. If your user already
     *
     * granted that permissions, it will call [onPermissionsGranted] method, otherwise it will
     *
     * make preparations to take permissions from user.
     *
     * @param businessRequest It is actually an answer to the question 'What are you going to do
     *
     * with these permissions?'. This parameter reflects your business need.
     *
     * @param exitOnDenied An optional parameter. It will make your activity finish when user denies
     *
     * permissions. Pass it **true** if your activity cannot live without permissions. **PLEASE**
     *
     * be careful and do **NOT** mix your fragment with its activity. You may want to exit only from
     *
     * your fragment, not the activity. In that case, pass it **false** and override
     *
     * [onPermissionsDenied] method and take necessary actions in it to exit from the fragment.
     *
     * Default is **false**
     *
     * @author Egemen Hamutçu
     */
    fun checkPermissionsAreGranted(businessRequest: BusinessRequest, exitOnDenied: Boolean = false) {
        this.businessRequest = businessRequest
        this.exitOnDenied = exitOnDenied
        if (::permissionManager.isInitialized) {
            val permissionRequests = permissionManager.getPermissionRequestsFromBusiness(businessRequest)

            context?.let {
                if (permissionManager.areAllPermissionsGranted(it, permissionRequests)) {
                    onPermissionsGranted(businessRequest)
                } else {
                    permissionManager.prepareNativePermissionRequest(permissionRequests)
                }
            }
        }
    }

    /**
     * # ATTENTION!
     * ## Never override this method to show an [AlertDialog]. [PermissionFragment] handles it for you!
     *
     * You will only need this method when you have to do extra work in case of user denial for permissions.
     *
     * @author Egemen Hamutçu
     */
    @CallSuper
    open fun onPermissionsDenied() {
        if (exitOnDenied) {
            activity?.finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        activity?.let {
            if (::businessRequest.isInitialized) {
                permissionManager.handleRequestPermissionsResult(it, businessRequest, permissions, grantResults)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            if (isComingFromSettings && ::businessRequest.isInitialized) {
                isComingFromSettings = false
                permissionManager.handleResume(it)
            }
        }
    }
}