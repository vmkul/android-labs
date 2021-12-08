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

        binding.orderButton.setOnClickListener { displayOrder() }
    }

    private fun getPizzaSize(): String {
        return when (binding.sizeLayout.checkedRadioButtonId) {
            R.id.size_small -> "Small"
            R.id.size_medium -> "Medium"
            else -> "Large"
        }
    }

    private fun getPizzaThickness(): String {
        return when (binding.thicknessLayout.checkedRadioButtonId) {
            R.id.thickness_standard -> "Standard"
            else -> "Thin"
        }
    }

    private fun getExtras(): String {
        var res = ""

        for (option in binding.additionalLayout.children) {
            if (option is CheckBox) {
                if (option.isChecked) {
                    res += "${option.text}\n"
                }
            }
        }

        return res
    }

    private fun generateOrderText(): String {
        val pizzaName = binding.pizzaName.text
        val pizzaSize = getPizzaSize()
        val pizzaThickness = getPizzaThickness()

        if (pizzaName.isEmpty()) {
            return ""
        }

        var result =
            "Ordered pizza: $pizzaName\nSize: $pizzaSize\nThickness: $pizzaThickness\nExtras:\n"
        result += getExtras()

        return result
    }

    private fun displayOrder() {
        val orderText = generateOrderText()

        if (orderText.isEmpty()) {
            Toast.makeText(applicationContext, "Please enter pizza name!", Toast.LENGTH_SHORT)
                .show()
            binding.orderResult.text = ""
            return
        }

        binding.orderResult.text = orderText
    }
}
