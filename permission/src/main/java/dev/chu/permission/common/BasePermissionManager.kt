package dev.chu.permission.common

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dev.chu.permission.common.model.PermissionResult

abstract class BasePermissionManager : Fragment() {

    protected abstract fun onPermissionResult(permissionResult: PermissionResult)

    private val rationalRequest = mutableMapOf<Int, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() &&
            grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        ) {
            onPermissionResult(PermissionResult.PermissionGranted(requestCode))
        } else if (permissions.any { shouldShowRequestPermissionRationale(it) }) {
            onPermissionResult(
                PermissionResult.PermissionDenied(requestCode,
                    permissions.filterIndexed { index, _ ->
                        grantResults[index] == PackageManager.PERMISSION_DENIED
                    }
                )
            )
        } else {
            onPermissionResult(
                PermissionResult.PermissionDeniedPermanently(requestCode,
                    permissions.filterIndexed { index, _ ->
                        grantResults[index] == PackageManager.PERMISSION_DENIED
                    }
                ))
        }
    }

    protected fun requestPermissions(requestId: Int, vararg permissions: String) {

        rationalRequest[requestId]?.let {
            requestPermissions(permissions, requestId)
            rationalRequest.remove(requestId)
            return
        }

        val notGranted = permissions.filter {
            ContextCompat.checkSelfPermission(
                requireActivity(),
                it
            ) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        when {
            notGranted.isEmpty() ->
                onPermissionResult(PermissionResult.PermissionGranted(requestId))
            notGranted.any { shouldShowRequestPermissionRationale(it) } -> {
                rationalRequest[requestId] = true
                onPermissionResult(PermissionResult.ShowRational(requestId))
            }
            else -> {
                requestPermissions(notGranted, requestId)
            }
        }
    }
}
