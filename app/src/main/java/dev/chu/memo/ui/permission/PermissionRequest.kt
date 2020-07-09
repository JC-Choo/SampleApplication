package dev.chu.memo.ui.permission

import android.Manifest

/**
 * 이 클래스는 어플리케이션의 설정 메뉴에 있는 안드로이드의 권한을 반영한 것
 * 값은 이전 값에 2배를 하는데, 이 접근은 비트 연산을 하기 위함
 */

enum class PermissionRequest(val request: Int) {
    CAMERA(1),
    STORAGE(2),
    LOCATION(4),
    MICROPHONE(8),
    PHONE(16);

    /**
     * 네이티브 [Manifest.permission]s 을 반영.
     * [Manifest.permission]s은 [PermissionRequest]에 따라 권한을 얻는데 필요한 것들을 정의
     *
     * @return [PermissionRequest]에 대한 [Manifest.permission]s 을 포함한 Array<String>
     */
    fun getPermissionTextArray(): Array<String> = when (this) {
        CAMERA -> arrayOf(Manifest.permission.CAMERA)
        STORAGE -> arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        LOCATION -> arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        MICROPHONE -> arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
        )
        PHONE -> arrayOf(Manifest.permission.READ_PHONE_STATE)
    }

    companion object {

        /**
         * [getPermissionTextArray] method 의 반대.
         * [permission]을 확인하고 [PermissionRequest]가 주어진 [Manifest.permission]을 반영하는 것을 정의
         *
         * @param permission 은 [Manifest.permission]'s [String] 값 중 하나
         *
         * @return [permission]에 따른 [PermissionRequest]의 값.
         * [permission]이 [Manifest.permission]'s 값 중 하나가 아니거나 [PermissionRequest] 중 어떤 거에 의해 권한이 사용되는게 아니라면, null을 리턴.
         *
         * @see [Manifest.permission]
         */
        fun getPermissionRequestFromText(permission: String): PermissionRequest? =
            when (permission) {
                Manifest.permission.CAMERA -> CAMERA
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE -> STORAGE
                Manifest.permission.ACCESS_FINE_LOCATION -> LOCATION
                Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS -> MICROPHONE
                Manifest.permission.READ_PHONE_STATE -> PHONE
                else -> null
            }
    }
}