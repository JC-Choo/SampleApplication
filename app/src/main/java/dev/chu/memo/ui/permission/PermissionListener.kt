package dev.chu.memo.ui.permission

/**
 * [PermissionManager] 의 메신저
 *
 * [PermissionManager] 와 [PermissionActivity], [PermissionFragment], [PermissionDialogFragment] 이거 사이의 관리자 역할이자,
 * [PermissionManager]에 의해 발생하는 이벤트를 가져오는 애!
 */
interface PermissionListener {
    /**
     * 안드로이드의 네이티브 권한들이 요청에 준비됐을 때 가져오는(invoke : 가져오다, 부르다, 호출하다) 이벤트닼
     */
    fun onNativePermissionRequestReady(permissions: Array<String>, requestCode: Int)

    /**
     * 이 이벤트는 [businessRequest]에 의해 필요한 모든 권한들이 나머지 작업을 처리하기 위해 부여될 때 가져온다.
     */
    fun grantPermissions(businessRequest: BusinessRequest)

    /**
     * 이 이벤트는 사용자가 영구적으로 어떤 권한을 거절했을 때 호출되며, 스스로 설정 메뉴에서 해당 권한을 부여(grant)해야만 한다.
     */
    fun navigateToSettings()

    /**
     * 이 이벤트는 사용자가 어떤 권한을 거절하거나 프로세스에서 더 이상 원하지 않을 때 호출한다.
     */
    fun deny()

    /**
     * 이 이벤트는 사용자가 rationale dialog에 직면하거나 설정 메뉴로부터 온 권한을 다시 부여할 때 호출된다.
     */
    fun retry()
}