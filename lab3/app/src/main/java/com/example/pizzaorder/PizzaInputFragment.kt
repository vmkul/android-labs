package com.example.pizzaorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.pizzaorder.databinding.PizzaInputBinding

class PizzaInputFragment : Fragment(R.layout.pizza_input) {
    private var _binding: PizzaInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PizzaInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.orderButton.setOnClickListener {
            val result = generateOrderText()
            setFragmentResult("orderButtonPressed", bundleOf("orderText" to result))
        }
        binding.viewOrderButton.setOnClickListener {
            setFragmentResult("viewButtonPressed", bundleOf("" to ""))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}