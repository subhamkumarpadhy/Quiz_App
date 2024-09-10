package com.example.quizical.global_utils.file_utils

import android.content.Context
import androidx.annotation.RawRes
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun File.moveTo(file: File) {
    if (!this.exists())
        return

    this.copyTo(file)
    this.delete()
}


fun String.toFileName(): String {
    return this.replace("/", "_").replace(":", "_")
}

fun Context.readRawFile(@RawRes resourceId: Int): String {
    val inputStream = resources.openRawResource(resourceId)
    val stringBuilder = StringBuilder()

    try {
        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append('\n')
        }
        reader.close()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream.close()
    }

    return stringBuilder.toString()
}