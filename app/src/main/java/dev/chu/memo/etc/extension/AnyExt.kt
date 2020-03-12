package dev.chu.memo.etc.extension

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
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