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
    private lateinit var dataFileManager: FileManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras
        if (extras != null) {
            val filename = extras.getString("dataFilename")

            if (filename != null) {
                dataFileManager = FileManager(filename, applicationContext)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.orderData.text = getOrderText()
    }

    private fun getOrderText(): String {
        if (!this::dataFileManager.isInitialized) {
            return "There is no order data stored"
        }

        return try {
            val res = dataFileManager.readFileContent()
            if (res.isEmpty()) "There is no order data stored"
            else res
        } catch (e: Exception) {
            "There is no order data stored"
        }
    }
}
