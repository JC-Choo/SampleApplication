package dev.chu.memo.etc.extension

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dev.chu.memo.etc.util.ImageUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


// 이미지 파일을 생성하는 메소드 -> 이미지가 저장된 파일을 만드는게 아니라 이미지가 저장될 파일을 만드는 함수
fun Context.makeImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("ko")).format(Date())
    val imageFileName = "JPEG_${timeStamp}_"
    // 일반적으로 사용자가 기기 카메라로 캡처한 사진은 기기의 공용 외부 저장소에 저장되므로 모든 앱에서 액세스할 수 있습니다.
    val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera")
    // 사진을 앱 이외에는 비공개로 두려면 대신 getExternalFilesDir()에서 제공하는 디렉터리를 사용
//    val storageDir = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Camera")

    if (!storageDir.exists())
        storageDir.mkdirs()

    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir          /* directory */
    )
}

private var currentPhotoPath: String = ""
@Throws(IOException::class)
fun AppCompatActivity.createImageFile(): File {
    // External public storage
    /**
     * 원본 크기의 사진 저장
     * 일반적으로 사용자가 기기 카메라로 캡처한 사진은 기기의 공용 외부 저장소에 저장되므로 모든 앱에서 액세스할 수 있습니다.
     * 사진을 공유하기 위한 적절한 디렉터리는 DIRECTORY_PICTURES를 인수로 사용하여 getExternalStoragePublicDirectory()에서 제공합니다.
     * 이 메서드에서 제공하는 디렉터리는 모든 앱에서 공유하기 때문에 이 디렉터리를 읽고 쓰려면 READ_EXTERNAL_STORAGE와 WRITE_EXTERNAL_STORAGE 권한이 각각 필요합니다.
     * 쓰기 권한은 암시적으로 읽기를 허용하므로 외부 저장소에 쓰려면 다음과 같이 하나의 권한만 요청하면 됩니다.
     *
     * 그러나 사진을 앱 이외에는 비공개로 두려면 대신 getExternalFilesDir()에서 제공하는 디렉터리를 사용
     * Android 4.3 이하 버전에서는 이 디렉터리에 쓸 때도 WRITE_EXTERNAL_STORAGE 권한이 필요합니다.
     * Android 4.4부터는 이 디렉터리를 다른 앱에서 액세스할 수 없으므로 더 이상 권한이 필요 없으며 다음과 같이 maxSdkVersion 속성을 추가하여 Android 이전 버전에서만 권한이 요청되도록 선언할 수 있습니다.
     *
     * 참고 : getExternalFilesDir() 또는 getFilesDir()에서 제공한 디렉터리에 저장한 파일은 사용자가 앱을 제거할 때 삭제
     *
     * 즉, 공용 외부 저장소에 저장하기 위해선 READ_EXTERNAL_STORAGE와 WRITE_EXTERNAL_STORAGE 권한이 각각 필요하며, getExternalStoragePublicDirectory()를 통해 구성되고,
     * 비공개로 두기 위해선 getExternalFilesDir()에서 제공하는 디렉터리(앱 내부 패키지)를 사용하며, 4.4 이후 부터는 WRITE_EXTERNAL_STORAGE 권한 조차 필요 없다.
     */
    Log.d(TAG, "External public root dir: " + Environment.getExternalStorageDirectory())
    Log.d(TAG, "External public file dir: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES))
    Log.d(TAG, "External public file dir: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM))
    Log.d(TAG, "External public root dir: " + getExternalFilesDir(Environment.DIRECTORY_DCIM))
    Log.d(TAG, "External public root dir: " + getExternalFilesDir(Environment.DIRECTORY_PICTURES))

    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("ko")).format(Date())
//    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera")
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = absolutePath
        Log.d(TAG, "External public root dir: $currentPhotoPath")
    }
}

fun Context.createImageFileInternalStorage(): File {
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("ko")).format(Date())
    val storageDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Camera")

    if (!storageDir.exists()) {
        Log.d(TAG, "mCurrentPhotoPath1 = $storageDir");
        storageDir.mkdirs();
    }

    return File.createTempFile(
        "JPEG_${timeStamp}_",
        ".jpg",
        storageDir
    ).apply {
        Log.i(TAG, "result : name = ${this.name}")
        Log.i(TAG, "result : path = ${this.path}")
        Log.i(TAG, "result : storageDir = $storageDir")
        Log.i(TAG, "result : absolutePath = $absolutePath")
        currentPhotoPath = absolutePath
        Log.i(TAG, "internal root dir: $currentPhotoPath")
    }
}

fun getCurrentPhotoPath() = currentPhotoPath

/* Checks if external storage is available for read and write */
// 반환된 상태가 MEDIA_MOUNTED 라면 파일을 읽고 쓸 수 있습니다.
fun isExternalStorageWritable(): Boolean =
    Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

/* Checks if external storage is available to at least read */
// 반환된 상태가 MEDIA_MOUNTED_READ_ONLY 라면 파일을 읽을 수만 있습니다.
fun isExternalStorageReadable(): Boolean =
    Environment.getExternalStorageState() in setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)

fun <T> Single<T>.with(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Context.bitmapDescriptorFromVector(
    @DrawableRes vectorResId: Int
): BitmapDescriptor? {
    val vectorDrawable =
        ContextCompat.getDrawable(this, vectorResId)
    vectorDrawable!!.setBounds(
        0,
        0,
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight
    )
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}





interface OnImageCropListener {
    fun onSuccess(isSendToCropApp: Boolean, uri: Uri)
    fun onFail()
}

fun Activity.cropImage(
    photoUri: Uri?,
    requestCode: Int,
    startActivityImageResult: (Intent, Int) -> Unit,
    listener: OnImageCropListener? = null
): Uri? {
    photoUri?.let {
        try {
            val intent = Intent("com.android.camera.action.CROP")
            intent.setDataAndType(photoUri, "image/*")

            var photoActivityInfo: ResolveInfo? = null
            val list = packageManager.queryIntentActivities(intent, 0)
            for (activity in list) {
                val isActivityPackageNameIsPhoto = activity.activityInfo.packageName.toString().contains("com.google.android.apps.photos", ignoreCase = true)
                val isActivityPackageNameIsGallery3 = activity.activityInfo.packageName.toString().contains("com.android.gallery3d", ignoreCase = true)
                if (isActivityPackageNameIsPhoto || isActivityPackageNameIsGallery3) {
                    photoActivityInfo = activity
                    break
                }
            }

            if (photoActivityInfo == null) {
                this@cropImage.centerCrop(photoUri, listener)
            } else {
                grantUriPermission(
                    photoActivityInfo.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                intent.putExtra("crop", "true")
//                intent.putExtra("outputX", 200); // crop한 이미지의 x축 크기, 결과물의 크기
//                intent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
//                intent.putExtra("aspectX", 1)     // 크롭 박스 x축 비율 -> 1:1이면 정사각형
//                intent.putExtra("aspectY", 1)     // 크롭 박스 y축 비율
                intent.putExtra("scale", true)

//                val folder = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Camera")
//                val tempFile = File(folder.toString(), makeImageFile().name)
                val tempFile = createImageFileInternalStorage()

                val newPhotoUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", tempFile)

                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                intent.putExtra("return-data", false)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, newPhotoUri)
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()) //Bitmap 형태로 받기 위해 해당 작업 진행

                startActivityImageResult(Intent(intent).apply {
                    val res = photoActivityInfo

                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

                    grantUriPermission(
                        res.activityInfo.packageName,
                        newPhotoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
                }, requestCode)

                listener?.let {
                    if (newPhotoUri != null) {
                        it.onSuccess(true, newPhotoUri)
                    } else {
                        it.onFail()
                    }
                } ?: run {
                    return newPhotoUri
                }
            }
        } catch (e: SecurityException) { // for Xiaomi..
            this@cropImage.centerCrop(photoUri, listener)
        }
    }
    return null
}

private fun Activity.centerCrop(
    photoUri: Uri?,
    listener: OnImageCropListener? = null
) {
    photoUri?.let { url ->
        listener?.let {
            try {
//                var bitmap: Bitmap? = MediaStore.Images.Media.getBitmap(this.contentResolver, url)
                var bitmap = if(Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, url)
                } else {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, url))
                }

                if (bitmap != null) {
                    val ei = ExifInterface(ImageUtil.getImageRealPathFromURI(this.contentResolver, url))
                    val orientation =
                        ei.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED
                        )
                    val angle = when (orientation) {
                        ExifInterface.ORIENTATION_ROTATE_90 -> 90.0f
                        ExifInterface.ORIENTATION_ROTATE_180 -> 180.0f
                        ExifInterface.ORIENTATION_ROTATE_270 -> 270.0f
                        ExifInterface.ORIENTATION_NORMAL -> 0.0f
                        else -> 0.0f
                    }
                    bitmap = ImageUtil.rotateImage(bitmap, angle)

                    val width = bitmap.width.coerceAtMost(bitmap.height)
                    val resultBitmap = ImageUtil.centerCrop(
                        bitmap,
                        width,
                        width
                    )

                    val bytes = ByteArrayOutputStream()
                    resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

                    val path =
//                        if(Build.VERSION.SDK_INT < 29) {
                        MediaStore.Images.Media.insertImage(
                            this.contentResolver,
                            resultBitmap,
                            "unknown",
                            null
                        )
                        /**
                         * insertImage is deprecated.
                         * -> MediaColumns.IS_PENDING 로 대체
                         *
                         * MediaColumns.IS_PENDING
                         * 미디어 항목이 보류 중이고 여전히 소유자에 의해 삽입되는 중인지를 가리키는 플래그.
                         * 플래그가 설정되어있는 동안, 항목의 소유자만이 기본 파일을 열 수 있다; 다른 파일로부터 요청은 거절될 것이다.
                         *
                         * 보류중인 항목들이 "0"으로 field를 설정함으로써 게시될때 까지, 또는 "DATE_EXPIRES" 에 의해 정의됨으로써 만료될떄 까지 유지될 것이다.
                         * 이 상수는 ContentValues or Cursor 객체를 통해 ContentProvider와 함께 사용될 수 있는 column(열) 이름을 표현한다.
                         * 이 열에 저장된 값은 Cursor#FIELD_TYPE_INTEGER 이다.
                         */
//                    } else {
//                        val values = ContentValues().apply {
//                            put(MediaStore.Images.Media.TITLE, "unknown")
//                            putNull(MediaStore.Images.Media.DESCRIPTION)
//                            put(MediaStore.MediaColumns.IS_PENDING, 1)
//                        }
//                        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values).toString()
//                    }
                    listener.onSuccess(false, Uri.parse(path))
                }
            } catch (e: FileNotFoundException) {
                listener.onFail()
            } catch (e: IOException) {
                listener.onFail()
            }
        }
    } ?: run {
        listener?.onFail()
    }
}