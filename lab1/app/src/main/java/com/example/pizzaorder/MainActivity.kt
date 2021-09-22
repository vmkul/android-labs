package com.example.pizzaorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.view.children
import com.example.pizzaorder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.orderButton.setOnClickListener{ displayOrder() }
    }

    private fun displayOrder() {
        val pizzaName = binding.pizzaName.text

        if (pizzaName.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter pizza name!", Toast.LENGTH_SHORT).show()
            binding.orderResult.text = ""
            return
        }

        val pizzaSize = when (binding.sizeLayout.checkedRadioButtonId) {
            R.id.size_small -> "Small"
            R.id.size_medium -> "Medium"
            else -> "Large"
        }

        val pizzaThickness = when (binding.thicknessLayout.checkedRadioButtonId) {
            R.id.thickness_standard -> "Standard"
            else -> "Thin"
        }

        var result = "Ordered pizza: $pizzaName\nSize: $pizzaSize\nThickness: $pizzaThickness\nExtras:\n"

        for (option in binding.additionalLayout.children) {
            if (option is CheckBox) {
                if (option.isChecked) {
                    result += "${option.text}\n"
                }
            }
        }

        binding.orderResult.text = result
    }
}