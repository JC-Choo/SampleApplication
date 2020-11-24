package dev.chu.memo.etc.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.provider.MediaStore
import kotlin.math.abs

object ImageUtil {
    fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix().apply {
            postRotate(angle)
        }
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }

    /**
     * A potentially expensive operation to crop the given Bitmap so that it fills the given
     * dimensions. This operation is significantly less expensive in terms of memory if a mutable
     * Bitmap with the given dimensions is passed in as well.
     *
     * @param pool     The BitmapPool to obtain a bitmap from.
     * @param inBitmap   The Bitmap to resize.
     * @param width    The width in pixels of the final Bitmap.
     * @param height   The height in pixels of the final Bitmap.
     * @return The resized Bitmap (will be recycled if recycled is not null).
     */
    fun centerCrop(inBitmap: Bitmap, width: Int, height: Int): Bitmap {
        if (inBitmap.width == width && inBitmap.height == height) {
            return inBitmap
        }
        // From ImageView/Bitmap.createScaledBitmap.
        val scale: Float
        val dx: Float
        val dy: Float
        val matrix = Matrix()

        if (inBitmap.width * height > width * inBitmap.height) {
            scale = height.toFloat() / inBitmap.height.toFloat()
            dx = (width - inBitmap.width * scale) * 0.5f
            dy = 0f
        } else {
            scale = width.toFloat() / inBitmap.width.toFloat()
            dx = 0f
            dy = (height - inBitmap.height * scale) * 0.5f
        }

        matrix.setScale(scale, scale)
        matrix.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())

        return Bitmap.createBitmap(
            inBitmap,
            abs(dx).toInt(),
            abs(dy).toInt(),
            width,
            height,
            matrix,
            true
        )
    }

    fun getImageRealPathFromURI(cr: ContentResolver, contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media._ID)
        val cursor = cr.query(
            contentUri,
            proj, // Which columns to return
            null, // WHERE clause; which rows to return (all rows)
            null, // WHERE clause selection arguments (none)
            null
        ) // Order-by clause (ascending by name)

        return if (cursor == null) {
            contentUri.path!!
        } else {
            val path = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
            cursor.moveToFirst()
            val tmp = cursor.getString(path)
            cursor.close()
            tmp
        }
    }
}