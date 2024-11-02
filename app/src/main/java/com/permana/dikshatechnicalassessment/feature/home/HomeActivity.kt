package com.permana.dikshatechnicalassessment.feature.home

import android.content.Intent
import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout
import com.permana.dikshatechnicalassessment.core.component.BaseActivity
import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.utils.extenstion.collectFlow
import com.permana.dikshatechnicalassessment.core.utils.extenstion.gone
import com.permana.dikshatechnicalassessment.core.utils.extenstion.showToast
import com.permana.dikshatechnicalassessment.core.utils.extenstion.visible
import com.permana.dikshatechnicalassessment.databinding.ActivityHomeBinding
import com.permana.dikshatechnicalassessment.feature.cart.CartActivity
import com.permana.dikshatechnicalassessment.feature.detailproduct.DetailProductActivity
import com.permana.dikshatechnicalassessment.feature.home.adapter.FilterAdapter
import com.permana.dikshatechnicalassessment.feature.home.adapter.ProductAdapter
import com.permana.dikshatechnicalassessment.feature.home.helper.ItemFilter
import com.permana.dikshatechnicalassessment.feature.login.LoginActivity
import com.permana.dikshatechnicalassessment.feature.login.LoginViewModel
import com.permana.xsisassessment.core.utils.extenstion.getLaunch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity: BaseActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val authViewModel: LoginViewModel by viewModel()

    private val viewModel: HomeViewModel by viewModel()

    private val filterAdapter by lazy {
        FilterAdapter(::onFilterClicked)
    }

    private val productAdapter by lazy {
        ProductAdapter(::onProductClicked)
    }

    override fun setLayout(): View = binding.root

    override fun initView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)

        viewModel.fetchProductsCategories()
        viewModel.fetchProducts()
        with(binding) {
            rvCategories.adapter = filterAdapter
            rvCategories.itemAnimator = null

            rvProducts.adapter = productAdapter
            ivProfile.setOnClickListener {
                showProfile()
            }
            ivCart.setOnClickListener {
                startActivity(Intent(this@HomeActivity, CartActivity::class.java))
            }

            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                viewModel.fetchProducts(    filterAdapter.currentList.find { it.isActive }?.value ?: "")
            }
        }

        setFlowCollector()
    }

    private fun showProfile() {
        getLaunch {
            ProfileBottomSheet.newInstance(authViewModel.readUserData()).apply {
               onLogoutClick = {
                   getLaunch {
                       if (authViewModel.clearUserData() && viewModel.clearAllCart() != -1){
                           dismiss()
                           startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                           finish()
                       }
                   }
               }
            }.show(supportFragmentManager, ProfileBottomSheet.TAG)
        }
    }

    private fun setFlowCollector() {
        collectFlow(viewModel.productsCategories) { response ->
            when(response) {
                is DikshaResponse.Error -> {
                    showToast(response.message)
                }

                is DikshaResponse.Loading -> {

                }

                is DikshaResponse.Success -> {
                    val itemFilterList = arrayListOf<ItemFilter>()
                    itemFilterList.add(ItemFilter("all", "", true))
                    itemFilterList.addAll(
                        response.data.categories.map { category ->
                            ItemFilter(label = category, value = category, isActive = false)
                        }
                    )

                    filterAdapter.submitList(itemFilterList)
                }

                else -> Unit
            }
        }

        collectFlow(viewModel.products) { response ->
            when(response) {
                is DikshaResponse.Error -> {
                    showToast(response.message)
                }

                is DikshaResponse.Loading -> {
                    productAdapter.submitList(null)
                    binding.shimmerProduct.startShimmer()
                    binding.shimmerProduct.visible()
                }

                is DikshaResponse.Success -> {
                    productAdapter.submitList(response.data)
                    binding.shimmerProduct.stopShimmer()
                    binding.shimmerProduct.gone()
                }

                else -> Unit
            }
        }
    }

    private fun onFilterClicked(f: ItemFilter) {
        filterAdapter.currentList.find {
            it.isActive
        }?.isActive = false
        f.isActive = true
        filterAdapter.notifyItemRangeChanged(0, filterAdapter.itemCount)
        viewModel.fetchProducts(f.value)
    }

    private fun onProductClicked(id: Int) {
        DetailProductActivity.start(this@HomeActivity, id)
    }

}