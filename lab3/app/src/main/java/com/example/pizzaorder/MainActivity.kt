package com.example.pizzaorder


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pizzaorder.databinding.ActivityMainBinding
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataFileName = "order.txt"
    private lateinit var fileManager: FileManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fileManager = FileManager(dataFileName, applicationContext)

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
                switchActivityIntent.putExtra("dataFilename", dataFileName)
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
            fileManager.writeToFile(orderText)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Error: couldn't save your order!", Toast.LENGTH_SHORT)
                .show()
        }
        Toast.makeText(applicationContext, "Your order has been saved!", Toast.LENGTH_SHORT)
            .show()
    }
}
