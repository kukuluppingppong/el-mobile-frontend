package com.example.androidel.send

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.androidel.MyApplication
import okhttp3.*
import java.io.File
import java.io.IOException

object SendOkhttp {
    fun send(currentImageUri: Uri, context: Context,
    trainer_id: Int, title: String, time: String, amount: String, score: Int) {
        var body: RequestBody
        val file = File(absolutelyPath(currentImageUri, context))
        Log.e("갤러리 이미지 경로", absolutelyPath(currentImageUri, context).toString())

        body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file",
                file.name,
                RequestBody.create(MultipartBody.FORM, file))
            .addFormDataPart("title", title)
            .addFormDataPart("time", time)
            .addFormDataPart("amount", amount)
            .addFormDataPart("score", score.toString())
            .build()

        Log.e("태그", body.toString())

        val url = "http://ec2-13-125-255-47.ap-northeast-2.compute.amazonaws.com:8080/api/history/diet/${trainer_id}"

        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer ${MyApplication.prefs.token}")
            .post(body)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("태그1", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("태그1", response.toString())
            }
        })
    }

    fun absolutelyPath(fileUri: Uri, ctx: Context): String? {
        var fullPath: String? = null
        val column = "_data"
        var cursor = ctx.contentResolver.query(fileUri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            var document_id = cursor.getString(0)
            if (document_id == null) {
                for (i in 0 until cursor.columnCount) {
                    if (column.equals(cursor.getColumnName(i), ignoreCase = true)) {
                        fullPath = cursor.getString(i)
                        break
                    }
                }
            } else {
                document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
                cursor.close()
                val projection = arrayOf(column)
                try {
                    cursor = ctx.contentResolver.query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        MediaStore.Images.Media._ID + " = ? ",
                        arrayOf(document_id),
                        null)
                    if (cursor != null) {
                        cursor.moveToFirst()
                        fullPath = cursor.getString(cursor.getColumnIndexOrThrow(column))
                    }
                } finally {
                    cursor.close()
                }
            }
        }
        return fullPath
    }
}