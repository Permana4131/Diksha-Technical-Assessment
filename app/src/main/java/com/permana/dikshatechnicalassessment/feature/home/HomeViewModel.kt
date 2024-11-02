package com.permana.dikshatechnicalassessment.feature.home

import androidx.lifecycle.ViewModel
import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.data.product.api.model.CartItem
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductItem
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductsCategories
import com.permana.dikshatechnicalassessment.core.data.product.api.repository.ProductRepository
import com.permana.xsisassessment.core.utils.extenstion.getLaunch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll

class HomeViewModel(
    val productRepository: ProductRepository
): ViewModel() {

    private val _productsCategories = MutableSharedFlow<DikshaResponse<ProductsCategories>>()
    val productsCategories = _productsCategories.asSharedFlow()

    private val _products = MutableSharedFlow<DikshaResponse<List<ProductItem>>>()
    val products = _products.asSharedFlow()

    private val _product = MutableSharedFlow<DikshaResponse<ProductItem>>()
    val product = _product.asSharedFlow()

    private val _cartStatus = MutableSharedFlow<DikshaResponse<Boolean>>()
    val cartStatus = _cartStatus.asSharedFlow()

    private val _cartItems = MutableSharedFlow<DikshaResponse<List<CartItem>>>()
    val cartItems = _cartItems.asSharedFlow()

    fun fetchProductsCategories() {
        getLaunch {
            _productsCategories.emitAll(productRepository.fetchProductsCategories())
        }
    }

    fun fetchProducts(categories: String = "") {
        getLaunch {
            _products.emitAll(productRepository.fetchProducts(categories))
        }
    }

    fun fetchProduct(id: Int) {
        getLaunch {
            _product.emitAll(productRepository.fetchProduct(id))
        }
    }

    fun addProductToCart(cartItem: CartItem) {
        getLaunch {
            _cartStatus.emitAll(productRepository.addToCart(cartItem))
        }
    }

    fun getCartItems() {
        getLaunch {
            _cartItems.emitAll(productRepository.getCartItems())
        }
    }

    fun updateCartItemQuantity(productId: Int, quantity: Int) {
        getLaunch {
           _cartStatus.emitAll(productRepository.updateCartItemQuantity(productId, quantity))
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        getLaunch {
            _cartStatus.emitAll(productRepository.removeFromCart(cartItem))
        }
    }

    suspend fun clearAllCart(): Int = productRepository.deleteAllCart()
}