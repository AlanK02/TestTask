package com.alan.testtask.data.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import java.io.InputStream


fun getFileName(uri: Uri, context: Context): String {
    var name = "avatar.jpg"
    val contentResolver: ContentResolver = context.contentResolver
    val cursor = contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (index != -1) {
                name = it.getString(index)
            }
        }
    }
    return name
}

fun uriToBase64(uri: Uri, context: Context): String {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val bytes = inputStream?.readBytes()
    return Base64.encodeToString(bytes, Base64.DEFAULT)
}
