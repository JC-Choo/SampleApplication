package dev.chu.memo.ui.crop

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import dev.chu.memo.GlobalApplication
import dev.chu.memo.R
import dev.chu.memo.common.Const
import dev.chu.memo.etc.extension.*
import dev.chu.memo.etc.shared.Prefs
import kotlinx.android.synthetic.main.activity_crop.*
import java.io.File
import java.io.IOException

// 참고 : https://g-y-e-o-m.tistory.com/48
// 자세한 주석 참고 : AddActivity

class CropActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_TAKE_PHOTO = 2222
        private const val REQUEST_TAKE_ALBUM = 3333
        private const val REQUEST_IMAGE_CROP = 4444
    }

    private var photoUri: Uri? = null
    private val prefs: Prefs by lazy { GlobalApplication.prefs!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)

        Log.i(TAG, "prefs.intExamplePref = ${prefs.intExamplePref}")
        prefs.intExamplePref = 10 + prefs.intExamplePref
        Log.i(TAG, "prefs.intExamplePref = ${prefs.intExamplePref}")

        setOnClickEvent()
    }

    private fun setOnClickEvent() {
        crop_bt_camera.setOnClickListener {
            setPermission()
            dispatchTakePictureIntent()
        }

        crop_bt_galley.setOnClickListener {
            setPermission()
            doTakeGalleryAction()
        }
    }

    private fun setPermission() {
        if (isPermissionsVersion() && !hasPermissions(*Const.usingPermissions)) {
            checkUsingPermission(Const.usingPermissions, Const.REQUEST_CODE_PERMISSIONS)
            showToast("권한을 확인해주세요.")
            return
        }

        if (!isExternalStorageWritable()) {
            showToast("저장 공간 접근 불가능한 기기입니다.\n해당 기기는 카메라 또는 갤러리에 접근 불가능합니다.")
            return
        }
    }

    // 사진 찍기
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFileInternalStorage()
                } catch (ex: IOException) {
                    ex.printStackTrace()
                    null
                }

                photoFile?.also {
                    photoUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    // 앨범에서 가져오기
    private fun doTakeGalleryAction() {
        startActivityForResult(Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            type = MediaStore.Images.Media.CONTENT_TYPE
        }, REQUEST_TAKE_ALBUM)
    }

    // Crop
    private fun cropImage() {
        Log.d(TAG, "cropImage photoUri = $photoUri, albumUri = $photoUri")

        cropImage(
            photoUri,
            REQUEST_IMAGE_CROP,
            ::startActivityForResult,
            object : OnImageCropListener {
                override fun onSuccess(isSendToCropApp: Boolean, uri: Uri) {
                    photoUri = uri
                }

                override fun onFail() {
                    photoUri = null
                    showToast("갤러리에서 이미지를 가져올 수 없습니다. 직접 촬영하여 등록해주세요.")
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Const.REQUEST_CODE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults.size == permissions.size) {
                    var isPermissionGranted = true
                    for (i in grantResults.indices) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            isPermissionGranted = false
                            break
                        }
                    }

                    if (!isPermissionGranted) {
                        alertDialog(R.string.please_need_permissions)
                    }
                }

                return
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.e(TAG, "resultCode = $resultCode, RESULT_OK = $RESULT_OK, requestCode = $requestCode")

        if (resultCode != RESULT_OK) {
            photoUri = null
            return
        }

        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {
                cropImage()
//                crop_iv.setImageURI(photoUri)
            }

            REQUEST_TAKE_ALBUM -> {
                data?.data?.let {
                    photoUri = it
                } ?: run {
                    photoUri = null
                }

                cropImage()

//                listImageUrls.add(ImageData(imageUrl = photoUri.toString()))
            }

            REQUEST_IMAGE_CROP -> {
                crop_iv.setImageURI(photoUri)
            }
        }
    }
}