package com.example.pizzaorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pizzaorder.databinding.OrderResultBinding

class OrderResultFragment : Fragment(R.layout.order_result) {
    private var _binding: OrderResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OrderResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setText(text: String) {
        binding.orderResult.text = text
    }
}