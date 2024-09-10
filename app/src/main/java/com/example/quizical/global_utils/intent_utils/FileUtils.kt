package com.example.quizical.global_utils.intent_utils

import android.content.Context
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import java.io.File
import java.io.FileOutputStream

/**
 * Opens a file picker allowing the user to select a file of a specific type.
 *
 * @param fileType The MIME type of the files that can be selected in the file picker.
 *                 Defaults to all file types if not specified.
 * @param onFilePicked A callback function that is invoked when a file is selected in the file picker.
 *                     The URI of the selected file is passed as a string to this callback function.
 */
inline fun ComponentActivity.openFilePicker(
    fileType: String = MimeTypes.MIME_ALL,
    crossinline onFilePicked: (fileUri: Uri?) -> Unit,
) {
    val filePickerLauncher = registerActivityResultLauncher(
        contract = ActivityResultContracts.GetContent(),
        callback = {
            onFilePicked(it)
        }
    )
    filePickerLauncher.launch(fileType)
}



/**
 * This is a code snippet for a Kotlin function.
 *
 * This function is used to save a file in a specified folder with a given content.
 * The file can be saved either in the internal storage or external storage of the device.
 *
 * Parameters:
 * - folderName: The name of the folder where the file will be saved.
 * - fileName: The name of the file to be saved.
 * - content: The content to be written to the file.
 * - saveInInternalStorage: A boolean flag indicating whether to save the file in the internal storage (default is true).
 * - callback: A callback function to be called after the file is saved, with a boolean parameter indicating the success of the operation (default is an empty callback).
 *
 * Example usage:
 * saveFile("Documents", "example.txt", "This is an example content", true) { success ->
 *     if (success) {
 *         // File saved successfully
 *     } else {
 *         // Failed to save the file
 *     }
 * }
 */
inline fun Context.saveFile(
    folderName: String,
    fileName: String,
    content: String,
    saveInInternalStorage: Boolean = true,
    crossinline callback: (Boolean) -> Unit = {},
) {
    val directory = if (saveInInternalStorage)
        File(this.filesDir, folderName)
    else
        File(this.getExternalFilesDir(null), folderName)

    if (!directory.exists())
        directory.mkdirs()

    val file = File(directory, fileName)

    try {
        FileOutputStream(file).use {
            it.write(content.toByteArray())
        }
        callback(true)
    } catch (e: Exception) {
        e.printStackTrace()
        callback(false)
    }
}


object MimeTypes {
    const val MIME_ALL = "*/*"
    const val MIME_IMAGE = "image/*"
    const val MIME_VIDEO = "video/*"
    const val MIME_AUDIO = "audio/*"
    const val MIME_TEXT = "text/*"
    const val MIME_APPLICATION = "application/*"
    const val MIME_PDF = "application/pdf"

    const val MIME_DOC = "application/msword"
    const val MIME_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    const val MIME_XLS = "application/vnd.ms-excel"
    const val MIME_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    const val MIME_PPT = "application/vnd.ms-powerpoint"
    const val MIME_PPTX =
        "application/vnd.openxmlformats-officedocument.presentationml.presentation"
    const val MIME_ZIP = "application/zip"
    const val MIME_RAR = "application/x-rar-compressed"
    const val MIME_APK = "application/vnd.android.package-archive"
    const val MIME_TXT = "text/plain"
    const val MIME_HTML = "text/html"
    const val MIME_CSS = "text/css"
    const val MIME_JS = "text/javascript"
    const val MIME_PHP = "text/php"
    const val MIME_XML = "text/xml"
    const val MIME_JSON = "application/json"
    const val MIME_SQL = "application/sql"
    const val MIME_JAVA = "text/x-java-source"
}



















