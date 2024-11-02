package com.permana.dikshatechnicalassessment.feature.detailproduct

import android.content.Context
import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.permana.dikshatechnicalassessment.R
import com.permana.dikshatechnicalassessment.core.component.BaseActivity
import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductItem
import com.permana.dikshatechnicalassessment.core.data.product.implementation.mapper.toCartItem
import com.permana.dikshatechnicalassessment.core.utils.extenstion.collectFlow
import com.permana.dikshatechnicalassessment.core.utils.extenstion.showToast
import com.permana.dikshatechnicalassessment.databinding.ActivityDetailProductBinding
import com.permana.dikshatechnicalassessment.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailProductActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun setLayout(): View = binding.root

    override fun initView() {
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getIntExtra(EXTRA_PRODUCT_ID, -1)
        if (productId != -1) {
            viewModel.fetchProduct(productId)
            setFlowCollector()
        } else {
            showToast("Product ID not found.")
            finish()
        }
    }

    private fun setFlowCollector() {
        collectFlow(viewModel.product) { response ->
            when (response) {
                is DikshaResponse.Error -> showToast(response.message)
                is DikshaResponse.Loading -> binding.progressBar.visibility = View.VISIBLE
                is DikshaResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    displayProductDetails(response.data)
                }
                else -> Unit
            }
        }

        collectFlow(viewModel.cartStatus) { response ->
            when (response) {
                is DikshaResponse.Error -> showToast(response.message)
                is DikshaResponse.Loading -> binding.progressBar.visibility = View.VISIBLE
                is DikshaResponse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    showToast("Succes add prodct to cart")
                }
                else -> Unit
            }
        }
    }

    private fun displayProductDetails(product: ProductItem) {
        with(binding) {
            tvTitle.text = product.title
            tvCategory.text = product.category
            tvDescription.text = product.description
            tvPrice.text = getString(R.string.dolar_price, product.price.toString())
            tvRating.text = "Rating: ${product.rating?.rate}/5"
            tvRatingCount.text = "(${product.rating?.count} reviews)"

            Glide.with(ivProductImage.context)
                .load(product.image)
                .placeholder(R.drawable.ic_downloading)
                .into(ivProductImage)

            btnAddToCart.setOnClickListener {
                viewModel.addProductToCart(product.toCartItem())
            }

            icBack.setOnClickListener {
                finish()
            }
        }
    }



    companion object {
        private const val EXTRA_PRODUCT_ID = "product_id"

        fun start(context: Context, productId: Int) {
            val intent = Intent(context, DetailProductActivity::class.java)
            intent.putExtra(EXTRA_PRODUCT_ID, productId)
            context.startActivity(intent)
        }
    }
}
