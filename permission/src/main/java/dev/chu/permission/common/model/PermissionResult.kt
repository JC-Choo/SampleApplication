package dev.chu.permission.common.model

/**
 * 상태 흐름을 조금 덜 흐릿하게 만들기 위해, sealed class를 만들어야만 하며
 * 시스템에서 가능한 모든 권한 콜백과 결과 코드처럼 필요한 정보를 포함해야만 한다.
 */
sealed class PermissionResult(val requestCode: Int) {
    class PermissionGranted(requestCode: Int) : PermissionResult(requestCode)
    class PermissionDenied(
        requestCode: Int,
        val deniedPermissions: List<String>
    ) : PermissionResult(requestCode) {
        fun test() {
            deniedPermissions.forEach {
                println("it = $it")
            }
        }
    }

    class ShowRational(requestCode: Int) : PermissionResult(requestCode)
    class PermissionDeniedPermanently(
        requestCode: Int,
        val permanentlyDeniedPermissions: List<String>
    ) : PermissionResult(requestCode)
}