package com.example.pizzaorder

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzaorder.databinding.ActivityDataBinding
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception


class DataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    private val DATA_FILE_NAME = "order.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.orderData.text = getOrderText()
    }

    private fun getOrderText(): String {
        val fileInputStream: InputStream
        try {
            fileInputStream = openFileInput(DATA_FILE_NAME)
        } catch (e: Exception) {
            return "There is no order data stored"
        }

        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String?
        while (run {
                text = bufferedReader.readLine()
                text
            } != null) {
            stringBuilder.append(text + "\n")
        }

        val res = stringBuilder.toString()

        return if (res.isEmpty()) {
            "There is no order data stored"
        } else {
            res
        }
    }
}
