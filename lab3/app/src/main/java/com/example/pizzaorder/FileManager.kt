package com.example.pizzaorder

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class FileManager(private val fileName: String, private val context: Context) {
    fun writeToFile(content: String) {
        val fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        fileOutputStream.use {
            fileOutputStream.write(content.toByteArray())
        }
    }

    fun readFileContent(): String {
        val fileInputStream: InputStream
        fileInputStream = context.openFileInput(fileName)
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String?

        fileInputStream.use {
            while (run {
                    text = bufferedReader.readLine()
                    text
                } != null) {
                stringBuilder.append(text + "\n")
            }
        }

        return stringBuilder.toString()
    }

    fun deleteFile() {
        context.deleteFile(fileName)
    }
}
