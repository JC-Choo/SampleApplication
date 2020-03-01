package dev.chu.memo.etc.extension

import android.content.Context
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
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
    // Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("ko")).format(Date())
    val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = absolutePath
    }
}

fun getCurrentPhotoPath() = currentPhotoPath