package com.example.pizzaorder


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzaorder.databinding.ActivityMainBinding
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val DATA_FILE_NAME = "order.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .setFragmentResultListener("orderButtonPressed", this) { requestKey, bundle ->
                val result = bundle.getString("orderText")

                if (result != null) {
                    displayOrder(result)
                    saveOrderToFile(result)
                }
            }

        supportFragmentManager
            .setFragmentResultListener("viewButtonPressed", this) { _, _ ->
                val switchActivityIntent = Intent(this, DataActivity::class.java)
                startActivity(switchActivityIntent)
            }
    }

    private fun displayOrder(orderText: String) {
        val fragment : OrderResultFragment = supportFragmentManager.findFragmentById(R.id.order_result_container) as OrderResultFragment;

        if (orderText.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter pizza name!", Toast.LENGTH_SHORT)
                .show()
            fragment.setText("");
            return
        }

        fragment.setText(orderText)
    }

    private fun saveOrderToFile(orderText: String) {
        if (orderText.isEmpty()) return
        try {
            val fileOutputStream = openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE)
            fileOutputStream.write(orderText.toByteArray())
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error: couldn't save your order!", Toast.LENGTH_SHORT)
                .show()
        }
        Toast.makeText(applicationContext, "Your order has been saved!", Toast.LENGTH_SHORT)
            .show()
    }
}
