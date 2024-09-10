package  com.example.quizical.global_utils.bitmap_utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun Uri?.downscaleToBitmapUri(context: Context, size: Int = 256): Uri? {
    if (this == null) return null

    var inputStream: InputStream? = null
    try {
        inputStream = context.contentResolver.openInputStream(this)

        val originalBitmap = BitmapFactory.decodeStream(inputStream) ?: return null

        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, size, size, true)

        val tempFile = createTempJpgFile(context)
        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        val fileOutputStream = FileOutputStream(tempFile)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.close()

        return Uri.fromFile(tempFile)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }

    return null
}


fun Uri.compressToBitmapUri(context: Context): Uri? {
    var inputStream: InputStream? = null

    try {
        inputStream = context.contentResolver.openInputStream(this)

        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        var quality = calculateQuality(originalBitmap, 100 * 1024)
        if (quality  <= 10)
            quality = 10
        Log.d("TAG", "compressToBitmapUri: $quality")
        val byteArrayOutputStream = ByteArrayOutputStream()
        originalBitmap?.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)

        val compressedFile = createTempJpgFile(context)


        val fileOutputStream = FileOutputStream(compressedFile)
        fileOutputStream.write(byteArrayOutputStream.toByteArray())
        fileOutputStream.close()

        return Uri.fromFile(compressedFile)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }

    return null
}

private fun calculateQuality(bitmap: Bitmap?, targetSize: Int): Int {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    var quality = 100

    while (byteArrayOutputStream.size() > targetSize && quality > 0) {
        byteArrayOutputStream.reset()
        quality -= 5
        bitmap?.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
    }

    return quality
}

private fun createTempJpgFile(context: Context): File {
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
        "compressed_image",
        ".jpg",
        storageDir
    )
}
