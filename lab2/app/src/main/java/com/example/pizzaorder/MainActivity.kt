package com.example.pizzaorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast


import com.example.pizzaorder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .setFragmentResultListener("orderButtonPressed", this) { requestKey, bundle ->
                val result = bundle.getString("orderText")

                if (result != null) {
                    displayOrder(result)
                }
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
}
