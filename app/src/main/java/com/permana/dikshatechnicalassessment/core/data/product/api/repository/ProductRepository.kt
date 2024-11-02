package com.permana.dikshatechnicalassessment.core.data.product.api.repository

import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.data.product.api.model.CartItem
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductItem
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductsCategories
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    /** fetch categories form api **/
    suspend fun fetchProductsCategories(): Flow<DikshaResponse<ProductsCategories>>

    /** fetch products form api **/
    suspend fun fetchProducts(categories: String = ""): Flow<DikshaResponse<List<ProductItem>>>

    /** fetch product form api **/
    suspend fun fetchProduct(id: Int): Flow<DikshaResponse<ProductItem>>

    suspend fun addToCart(cartItem: CartItem): Flow<DikshaResponse<Boolean>>

    suspend fun getCartItems(): Flow<DikshaResponse<List<CartItem>>>

    suspend fun updateCartItemQuantity(productId: Int, quantity: Int): Flow<DikshaResponse<Boolean>>

    suspend fun removeFromCart(cartItem: CartItem): Flow<DikshaResponse<Boolean>>

    suspend fun deleteAllCart(): Int
}