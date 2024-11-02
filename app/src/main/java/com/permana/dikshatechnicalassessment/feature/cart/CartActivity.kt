package com.permana.dikshatechnicalassessment.feature.cart

import android.content.Intent
import android.view.View
import com.permana.dikshatechnicalassessment.R
import com.permana.dikshatechnicalassessment.core.component.BaseActivity
import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.utils.extenstion.collectFlow
import com.permana.dikshatechnicalassessment.core.utils.extenstion.showToast
import com.permana.dikshatechnicalassessment.databinding.ActivityCartBinding
import com.permana.dikshatechnicalassessment.feature.cart.adapter.CartAdapter
import com.permana.dikshatechnicalassessment.feature.home.HomeViewModel
import com.permana.dikshatechnicalassessment.feature.summary.OrderSummaryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartActivity: BaseActivity() {

    private lateinit var binding: ActivityCartBinding

    private val viewModel: HomeViewModel by viewModel()

    override fun setLayout(): View = binding.root

    private val cartAdapter by lazy {
        CartAdapter(
            onIncreaseQuantity = {
                viewModel.updateCartItemQuantity(it.productId, it.quantity+1)
            },
            onDecreaseQuantity = {
                if (it.quantity > 1) {
                    viewModel.updateCartItemQuantity(it.productId, it.quantity-1)
                } else showToast("Can't decrease product")
            },
            onDeleteItem = {
                viewModel.removeFromCart(it)
            }
        )
    }

    override fun initView() {
        binding = ActivityCartBinding.inflate(layoutInflater)
        viewModel.getCartItems()
        with(binding) {
            rvCartItems.adapter = cartAdapter
            btnCheckout.setOnClickListener {
                val intent = Intent(this@CartActivity, OrderSummaryActivity::class.java)
                startActivity(intent)
            }

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

                is DikshaResponse.Empty -> {
                    showToast("No Data Found")
                    binding.btnCheckout.isEnabled = false
                    cartAdapter.submitList(null)
                    binding.tvValueTotalPrice.text = getString(R.string.zero)
                }

                is DikshaResponse.Success -> {
                   cartAdapter.submitList(response.data)
                    val totalPrice = response.data.sumOf { it.price * it.quantity }
                    binding.tvValueTotalPrice.text = getString(R.string.dolar_price, String.format("%.2f", totalPrice))
                }

                else -> Unit
            }
        }

        collectFlow(viewModel.cartStatus) { response ->
            when(response) {
                is DikshaResponse.Error -> {
                    showToast(response.message)
                }

                is DikshaResponse.Loading -> {

                }

                is DikshaResponse.Success -> {
                    viewModel.getCartItems()
                }

                else -> Unit
            }
        }
    }
}