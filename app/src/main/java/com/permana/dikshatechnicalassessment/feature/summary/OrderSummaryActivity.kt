package com.permana.dikshatechnicalassessment.feature.summary

import android.content.Intent
import android.view.View
import com.permana.dikshatechnicalassessment.R
import com.permana.dikshatechnicalassessment.core.component.BaseActivity
import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.utils.extenstion.collectFlow
import com.permana.dikshatechnicalassessment.core.utils.extenstion.showToast
import com.permana.dikshatechnicalassessment.databinding.ActivityCartBinding
import com.permana.dikshatechnicalassessment.databinding.ActivityOrderSummaryBinding
import com.permana.dikshatechnicalassessment.feature.cart.adapter.CartAdapter
import com.permana.dikshatechnicalassessment.feature.home.HomeViewModel
import com.permana.dikshatechnicalassessment.feature.summary.adapter.OrderSummaryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderSummaryActivity: BaseActivity() {
    private lateinit var binding: ActivityOrderSummaryBinding

    private val viewModel: HomeViewModel by viewModel()

    override fun setLayout(): View = binding.root

    private val summaryAdapter by lazy {
        OrderSummaryAdapter()
    }

    override fun initView() {
        binding = ActivityOrderSummaryBinding.inflate(layoutInflater)
        viewModel.getCartItems()
        with(binding) {
            rvOrderSummary.adapter = summaryAdapter
            ivBack.setOnClickListener {
                finish()
            }
        }

        setFlowCollector()
    }

    private fun setFlowCollector() {
        collectFlow(viewModel.cartItems) { response ->
            when(response) {
                is DikshaResponse.Error -> {
                    showToast(response.message)
                }

                is DikshaResponse.Loading -> {

                }

                is DikshaResponse.Success -> {
                    summaryAdapter.submitList(response.data)
                    val totalPrice = response.data.sumOf { it.price * it.quantity }
                    binding.tvTotalPrice.text = getString(R.string.total_dolar_price, String.format("%.2f", totalPrice))
                }

                else -> Unit
            }
        }
    }
}