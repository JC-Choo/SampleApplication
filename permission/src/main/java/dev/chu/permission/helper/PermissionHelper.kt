package dev.chu.permission.helper

import android.app.Activity
import android.content.pm.PackageManager
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

/**
 * 참고 : https://www.techyourchance.com/runtime-permissions-android/
 */
@UiThread
open class PermissionHelper(
    private var activity: Activity
) {

    interface Listener {
        fun onRequestPermissionsResult(requestCode: Int, result: PermissionsResult)
        fun onPermissionsRequestCanceled(requestCode: Int)
    }

    class PermissionsResult(
        val granted: List<MyPermission>,
        val denied: List<MyPermission>,
        val deniedDoNotAskAgain: List<MyPermission>
    )

    private var listeners: MutableList<Listener> = mutableListOf()
    fun registerListener(listener: Listener) {
        this.listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        this.listeners.remove(listener)
    }

    fun hasPermission(permission: MyPermission): Boolean =
        ContextCompat.checkSelfPermission(activity, permission.getAndroidPermission()) == PackageManager.PERMISSION_GRANTED

    fun hasAllPermissions(permissions: Array<MyPermission>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission.getAndroidPermission()) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun requestPermission(permission: MyPermission, requestCode: Int) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission.getAndroidPermission()),
            requestCode
        )
    }

    fun requestAllPermissions(permissions: Array<MyPermission>, requestCode: Int) {
        val androidPermissions = arrayOfNulls<String>(permissions.size)
        permissions.forEachIndexed { index, myPermission ->
            androidPermissions[index] = myPermission.getAndroidPermission()
        }
        ActivityCompat.requestPermissions(activity, androidPermissions, requestCode)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        androidPermissions: Array<String>,
        grantResults: IntArray
    ) {
        if (androidPermissions.isEmpty() || grantResults.isEmpty()) {
            notifyPermissionsRequestCancelled(requestCode)
        }

        val grantedPermissions: MutableList<MyPermission> = LinkedList()
        val deniedPermissions: MutableList<MyPermission> = LinkedList()
        val deniedAndDoNotAskAgainPermissions: MutableList<MyPermission> = LinkedList()

        var androidPermission: String
        var permission: MyPermission
        androidPermissions.forEachIndexed { index, s ->
            androidPermission = s
            permission = MyPermission.fromAndroidPermission(androidPermission)
            when {
                grantResults[index] == PackageManager.PERMISSION_GRANTED -> {
                    grantedPermissions.add(permission)
                }
                ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermission) -> {
                    deniedPermissions.add(permission)
                }
                else -> {
                    deniedAndDoNotAskAgainPermissions.add(permission)
                }
            }
        }

        val result = PermissionsResult(grantedPermissions, deniedPermissions, deniedAndDoNotAskAgainPermissions)
        notifyPermissionsResult(requestCode, result)
    }

    private fun notifyPermissionsResult(requestCode: Int, permissionsResult: PermissionsResult) {
        for (listener: Listener in listeners) {
            listener.onRequestPermissionsResult(requestCode, permissionsResult)
        }
    }

    private fun notifyPermissionsRequestCancelled(requestCode: Int) {
        for (listener: Listener in listeners) {
            listener.onPermissionsRequestCanceled(requestCode)
        }
    }
}