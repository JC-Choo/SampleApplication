package dev.chu.permission.helper

import android.Manifest

enum class MyPermission(private val androidPermission: String) {
    READ_PHONE_STATE(Manifest.permission.READ_PHONE_STATE),
    FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION);

    companion object {
        fun fromAndroidPermission(androidPermission: String): MyPermission {
            for (permission in values()) {
                if (permission.getAndroidPermission() == androidPermission) {
                    return permission
                }
            }
            throw RuntimeException("Android permission not supported yes : $androidPermission")
        }
    }

    fun getAndroidPermission() = androidPermission
}