package dev.chu.memo.ui.permission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dev.chu.memo.R

/**
 * Created by Egemen Hamutçu on 2020-01-02.
 *
 * # The Secret Superhero of All Permission Process
 *
 * 이 클래스는 권한 프로세스의 모든 비즈니스 로직을 관리하고 Android의 기본 권한 동작에 대한 충실한 제자?이다.
 *
 * [PermissionActivity], [PermissionFragment] and [PermissionDialogFragment]에서 field로서 동작하고,
 * 위 클래스들을 상속받은 어떤 클래스에서도 보이지 않는다.
 *
 * It secretly handles all difficulties of permission process in one place and make users happy with their choices. If one
 *
 * -apart from the classes above- directly tries to use this class will break its sacredness and
 *
 * will be punished by the burning god of Android with its cruel imp, Samsung Galaxy S3!
 *
 * @author Egemen Hamutçu
 */
class PermissionManager(private val permissionListener: PermissionListener) {

    /**
     * It reflects what you should request for permissions according to [businessRequest].
     *
     * @param businessRequest define what you want to do
     *
     * @return an [Array] of [PermissionRequest]s according to [businessRequest]
     *
     * @author Egemen Hamutçu
     */
    fun getPermissionRequestsFromBusiness(businessRequest: BusinessRequest): Array<PermissionRequest> {
        return when (businessRequest) {
            BusinessRequest.ADD_PHOTO_TO_ADVERT_FROM_CAMERA,
            BusinessRequest.READ_QR_FROM_CAMERA,
            BusinessRequest.TAKE_PHOTO_FOR_FIRM_LOGO_FROM_CAMERA -> arrayOf(PermissionRequest.CAMERA)
            BusinessRequest.ADD_PHOTO_TO_ADVERT_FROM_GALLERY,
            BusinessRequest.PICK_PHOTO_FOR_FIRM_LOGO_FROM_GALLERY -> arrayOf(PermissionRequest.STORAGE)
            BusinessRequest.LOCATION_TO_SHOW_ON_MAP -> arrayOf(PermissionRequest.LOCATION)
            BusinessRequest.VIDEO_CALL -> arrayOf(PermissionRequest.CAMERA, PermissionRequest.MICROPHONE, PermissionRequest.PHONE)
        }
    }

    /**
     * It checks whether all permissions given in [permissionRequests] are granted by the user
     *
     * before. It converts each item of [permissionRequests] to an [Array] of
     *
     * [android.Manifest.permission] value and calls [ContextCompat.checkSelfPermission] method to
     *
     * check each [android.Manifest.permission] is granted by the user.
     *
     * @param permissionRequests an [Array] of [PermissionRequest]s to check whether all of them
     *
     * are granted by the user.
     *
     * @return true if all permissions are granted, false otherwise.
     *
     * @author Egemen Hamutçu
     */
    fun areAllPermissionsGranted(context: Context, permissionRequests: Array<out PermissionRequest>): Boolean {
        return permissionRequests.all { permissionRequest ->
            permissionRequest.getPermissionTextArray().all { permission ->
                val permissionStatus = ContextCompat.checkSelfPermission(context, permission)
                permissionStatus == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    /**
     * Takes given [permissionRequests] and prepare them for Android's native permission request.
     *
     * Finally, it invokes [PermissionListener.onNativePermissionRequestReady] method.
     *
     * @author Egemen Hamutçu
     */
    fun prepareNativePermissionRequest(permissionRequests: Array<out PermissionRequest>) {
        val permissions = getPermissions(permissionRequests)
        val requestCode = getRequestCode(permissionRequests)
        permissionListener.onNativePermissionRequestReady(permissions, requestCode)
    }

    /**
     * Takes [permissionRequests] and convert them to a combination of [android.Manifest.permission]
     * in one [Array]. It is used to prepare Android's native permission request.
     *
     * @param permissionRequests an [Array] of [PermissionRequest]s provided by
     *
     * [getPermissionRequestsFromBusiness] method.
     *
     * @return an [Array] of [android.Manifest.permission].
     *
     * @author Egemen Hamutçu
     */
    private fun getPermissions(permissionRequests: Array<out PermissionRequest>): Array<String> {
        val permissions = ArrayList<String>()

        permissionRequests.forEach { permissionRequest ->
            val permissionTexts = permissionRequest.getPermissionTextArray()
            permissions.addAll(permissionTexts)
        }

        return permissions.toTypedArray()
    }

    /**
     * It sums [PermissionRequest.request]s to define request code. It is used to prepare Android's
     *
     * native permission request.
     *
     * @param permissionRequests an [Array] of [PermissionRequest]s provided by
     *
     * [getPermissionRequestsFromBusiness] method.
     *
     * @return sum of [PermissionRequest.request] in [permissionRequests].
     *
     * @author Egemen Hamutçu
     */
    private fun getRequestCode(permissionRequests: Array<out PermissionRequest>) = permissionRequests.sumBy { it.request }

    /**
     * It finds the string resource in **strings_permission.xml** of title according to
     *
     * [businessRequest].
     *
     * @param businessRequest define which title you want to show according to your business.
     *
     * @return a string resource in  **strings_permission.xml** defining the permission title.
     *
     * @author Egemen Hamutçu
     */
    @StringRes
    private fun getPermissionTitle(businessRequest: BusinessRequest): Int {
        return when (businessRequest) {
            BusinessRequest.ADD_PHOTO_TO_ADVERT_FROM_CAMERA,
            BusinessRequest.READ_QR_FROM_CAMERA,
            BusinessRequest.TAKE_PHOTO_FOR_FIRM_LOGO_FROM_CAMERA -> R.string.permission_title_camera
            BusinessRequest.ADD_PHOTO_TO_ADVERT_FROM_GALLERY,
            BusinessRequest.PICK_PHOTO_FOR_FIRM_LOGO_FROM_GALLERY -> R.string.permission_title_storage
            BusinessRequest.LOCATION_TO_SHOW_ON_MAP -> R.string.permission_title_location
            BusinessRequest.VIDEO_CALL -> R.string.permission_title_application
        }
    }

    /**
     * It finds the string resource in **strings_permission.xml** of rationale message according to
     *
     * [businessRequest]. It is used when user denies a permission.
     *
     * @param context We use it to access resources.
     *
     * @param businessRequest define which rationale message you want to show according to your
     *
     * business.
     *
     * @return a [String] defining rationale message.
     *
     * @author Egemen Hamutçu
     */
    private fun getMessageForRationale(context: Context, businessRequest: BusinessRequest): String {
        return when (businessRequest) {
            BusinessRequest.ADD_PHOTO_TO_ADVERT_FROM_CAMERA -> context.getString(R.string.permission_rationale_add_photo_to_advert_from_camera)
            BusinessRequest.READ_QR_FROM_CAMERA -> context.getString(R.string.permission_rationale_read_qr_from_camera)
            BusinessRequest.TAKE_PHOTO_FOR_FIRM_LOGO_FROM_CAMERA -> context.getString(R.string.permission_rationale_take_photo_for_firm_logo_from_camera)
            BusinessRequest.ADD_PHOTO_TO_ADVERT_FROM_GALLERY -> context.getString(R.string.permission_rationale_add_photo_to_advert_from_gallery)
            BusinessRequest.PICK_PHOTO_FOR_FIRM_LOGO_FROM_GALLERY -> context.getString(R.string.permission_rationale_pick_photo_for_firm_logo_from_gallery)
            BusinessRequest.LOCATION_TO_SHOW_ON_MAP -> context.getString(R.string.permission_rationale_location_to_show_on_map)
            BusinessRequest.VIDEO_CALL -> context.getString(R.string.permission_rationale_video_call)
        }
    }

    /**
     * It finds the string resource in **strings_permission.xml** of permanent denial message
     * according to [businessRequest]. It is used when user permanently denies a permission.
     *
     * @param context We use it to access resources.
     *
     * @param businessRequest define which permanent denial message you want to show according to
     * your business.
     *
     * @param deniedPermissions an [Array] of [android.Manifest.permission]
     *
     * @return a [String] defining the message of permanent denial.
     *
     * @author Egemen Hamutçu
     */
    private fun getMessageForPermanentDenial(context: Context, businessRequest: BusinessRequest, deniedPermissions: ArrayList<String>): String {
        return when (businessRequest) {
            BusinessRequest.ADD_PHOTO_TO_ADVERT_FROM_CAMERA -> context.getString(R.string.permission_permanent_denial_add_photo_to_advert_from_camera)
            BusinessRequest.READ_QR_FROM_CAMERA -> context.getString(R.string.permission_permanent_denial_read_qr_from_camera)
            BusinessRequest.TAKE_PHOTO_FOR_FIRM_LOGO_FROM_CAMERA -> context.getString(R.string.permission_permanent_denial_take_photo_for_firm_logo_from_camera)
            BusinessRequest.ADD_PHOTO_TO_ADVERT_FROM_GALLERY -> context.getString(R.string.permission_permanent_denial_add_photo_to_advert_from_gallery)
            BusinessRequest.PICK_PHOTO_FOR_FIRM_LOGO_FROM_GALLERY -> context.getString(R.string.permission_permanent_denial_pick_photo_for_firm_logo_from_gallery)
            BusinessRequest.LOCATION_TO_SHOW_ON_MAP -> context.getString(R.string.permission_permanent_denial_location_to_show_on_map)
            BusinessRequest.VIDEO_CALL -> {
                val deniedPermissionTexts = ArrayList<String>()
                deniedPermissions.mapNotNull { deniedPermission ->
                    PermissionRequest.getPermissionRequestFromText(deniedPermission)
                }.forEach { permissionRequest ->
                    val deniedPermission = when (permissionRequest) {
                        PermissionRequest.CAMERA -> context.getString(R.string.camera)
                        PermissionRequest.STORAGE -> context.getString(R.string.storage)
                        PermissionRequest.LOCATION -> context.getString(R.string.location)
                        PermissionRequest.MICROPHONE -> context.getString(R.string.microphone)
                        PermissionRequest.PHONE -> context.getString(R.string.phone)
                    }

                    if (!deniedPermissionTexts.contains(deniedPermission)) {
                        deniedPermissionTexts.add(deniedPermission)
                    }
                }

//                val deniedPermissionsConcatenated = StringUtil.concatStrings(", ", deniedPermissionTexts, 0)
//                context.getString(R.string.permission_permanent_denial_video_call, deniedPermissionsConcatenated, deniedPermissionsConcatenated)
                "result"
            }
        }
    }

    /**
     * This method is called when [Activity.onRequestPermissionsResult] or
     *
     * [androidx.fragment.app.Fragment.onRequestPermissionsResult] invoked to handle the result of
     *
     * permission. It invokes [PermissionListener.grantPermissions] method if all permissions are
     *
     * granted. If user denies any of permissions, it checks what to show either rationale or
     *
     * permanent denial dialog.
     *
     * @author Egemen Hamutçu
     */
    fun handleRequestPermissionsResult(activity: Activity, businessRequest: BusinessRequest, permissions: Array<out String>, grantResults: IntArray) {
        val deniedPermissions = ArrayList<String>()
        var areAllPermissionsGranted = true
        grantResults.indices.forEach { i ->
            val grantResult = grantResults[i]
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                areAllPermissionsGranted = false
                deniedPermissions.add(permissions[i])
            }
        }

        if (areAllPermissionsGranted) {
            permissionListener.grantPermissions(businessRequest)
        } else {
            val shouldShowRationale = deniedPermissions.any { deniedPermission ->
                ActivityCompat.shouldShowRequestPermissionRationale(activity, deniedPermission)
            }

            if (shouldShowRationale) {
                showPermissionRationaleDialog(activity, businessRequest)
            } else {
                showPermissionPermanentDenialDialog(activity, businessRequest, deniedPermissions)
            }
        }
    }

    /**
     * It shows a rationale dialog to the user why we want those permissions. We ask the user
     *
     * to try again to process our work. If user tries again, it invokes [PermissionListener.retry].
     *
     * If user taps on cancel, it invokes [PermissionListener.deny]
     *
     * @author Egemen Hamutçu
     */
    private fun showPermissionRationaleDialog(activity: Activity, businessRequest: BusinessRequest) {
        if (!activity.isFinishing) {
            val title = getPermissionTitle(businessRequest)
            val message = getMessageForRationale(activity, businessRequest)

            AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.retry) { _, _ ->
                    permissionListener.retry()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    if (!activity.isFinishing) {
                        dialog.dismiss()
                        permissionListener.deny()
                    }
                }
                .show()
        }
    }

    /**
     * It shows a permanently denial dialog for permissions. It explains why we cannot process
     *
     * further because of user's permanently denial for permissions and asks user to grant
     *
     * permissions in the settings menu of the application. If user taps on settings, it invokes
     *
     * [PermissionListener.navigateToSettings]. If user taps on cancel, it invokes
     *
     * [PermissionListener.deny]
     *
     * @author Egemen Hamutçu
     */
    private fun showPermissionPermanentDenialDialog(activity: Activity, businessRequest: BusinessRequest, deniedPermissions: ArrayList<String>) {
        if (!activity.isFinishing) {
            val title = getPermissionTitle(businessRequest)
            val message = getMessageForPermanentDenial(activity, businessRequest, deniedPermissions)

            AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.settings) { _, _ ->
                    permissionListener.navigateToSettings()
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    if (!activity.isFinishing) {
                        dialog.dismiss()
                        permissionListener.deny()
                    }
                }
                .show()
        }
    }

    /**
     * When user comes from the settings menu of the application, it shows a dialog to try again to
     *
     * continue process. If user taps on yes, it invokes [PermissionListener.retry]. If user taps on
     *
     * no, it invokes [PermissionListener.deny]
     *
     * @author Egemen Hamutçu
     */
    fun handleResume(activity: Activity) {
        if (!activity.isFinishing) {
            AlertDialog.Builder(activity)
                .setTitle(R.string.retry)
                .setMessage(R.string.retry_business)
                .setCancelable(false)
                .setPositiveButton(R.string.yes) { _, _ ->
                    permissionListener.retry()
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    if (!activity.isFinishing) {
                        dialog.dismiss()
                        permissionListener.deny()
                    }
                }
                .show()
        }
    }
}