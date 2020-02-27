package dev.chu.memo.etc.extension

import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

// 이미지 파일을 생성하는 메소드 -> 이미지가 저장된 파일을 만드는게 아니라 이미지가 저장될 파일을 만드는 함수
fun makeImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("ko")).format(Date())
    val imageFileName = "JPEG_${timeStamp}_"
    val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera")

    if (!storageDir.exists())
        storageDir.mkdirs()

    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir          /* directory */
    )
}