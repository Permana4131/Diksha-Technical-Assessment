package com.permana.dikshatechnicalassessment.core.data.product.implementation.repository

import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.data.product.api.model.CartItem
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductItem
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductsCategories
import com.permana.dikshatechnicalassessment.core.data.product.api.repository.ProductRepository
import com.permana.dikshatechnicalassessment.core.data.product.implementation.database.dao.CartDao
import com.permana.dikshatechnicalassessment.core.data.product.implementation.mapper.toCartItem
import com.permana.dikshatechnicalassessment.core.data.product.implementation.mapper.toCartItemEntity
import com.permana.dikshatechnicalassessment.core.data.product.implementation.mapper.toProductItem
import com.permana.dikshatechnicalassessment.core.data.product.implementation.mapper.toProductsCategories
import com.permana.dikshatechnicalassessment.core.data.product.implementation.remote.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepositoryImpl(
    private val productApi: ProductApi,
    private val cartDao: CartDao,
) : ProductRepository {

    override suspend fun fetchProductsCategories(): Flow<DikshaResponse<ProductsCategories>> =
        flow {
            emit(DikshaResponse.Loading)
            try {
                val response = productApi.fetchCategories()
                when {
//                response.success == false -> {
//                    emit(DikshaResponse.Error(message = response.statusMessage.orEmpty()))
//                }

                    response.isNullOrEmpty() -> {
                        emit(DikshaResponse.Error(message = "No Categories Found"))
                    }

                    else -> {
                        emit(DikshaResponse.Success(response.toProductsCategories()))
                    }
                }
            } catch (e: Exception) {
                emit(DikshaResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun fetchProducts(categories: String): Flow<DikshaResponse<List<ProductItem>>> =
        flow {
            emit(DikshaResponse.Loading)
            try {
                val response =
                    if (categories.isEmpty()) productApi.fetchProducts()
                    else productApi.fetchProductsByCategories(categories)
                when {
                    response.isNullOrEmpty() -> {
                        emit(DikshaResponse.Error(message = "No Categories Found"))
                    }

                    else -> {
                        emit(DikshaResponse.Success(response.map { it.toProductItem() }))
                    }
                }
            } catch (e: Exception) {
                emit(DikshaResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun fetchProduct(id: Int): Flow<DikshaResponse<ProductItem>> =
        flow {
            emit(DikshaResponse.Loading)
            try {
                val response = productApi.fetchSingleProduct(id)
                when {
                    response.title.isNullOrEmpty() -> {
                        emit(DikshaResponse.Error(message = "No Product Found"))
                    }

                    else -> {
                        emit(DikshaResponse.Success(response.toProductItem()))
                    }
                }
            } catch (e: Exception) {
                emit(DikshaResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun addToCart(cartItem: CartItem): Flow<DikshaResponse<Boolean>> {
        return flow {
            emit(DikshaResponse.Loading)
            val checkProduct = cartDao.findCartById(cartItem.productId)
            if (checkProduct.isNotEmpty()) {
                val product = checkProduct.first()
                cartDao.updateQuantity(product.productId, product.quantity +1)
            } else {
                cartDao.insert(cartItem.toCartItemEntity())
            }
            emit(DikshaResponse.Success(true))
        }
            .catch { emit(DikshaResponse.Error("Failed to add item to cart.")) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun getCartItems(): Flow<DikshaResponse<List<CartItem>>>  {
        return flow {
            emit(DikshaResponse.Loading)
            val cart = cartDao.getAllCartItems()
            when {
                cart.isEmpty() -> {
                    emit(DikshaResponse.Empty)
                }
                else -> {
                    emit(DikshaResponse.Success(cart.map { it.toCartItem() }))
                }

            }
        }
            .catch { DikshaResponse.Error("Invalid express loan local storage") }
            .flowOn( Dispatchers.IO)
    }

    override suspend fun updateCartItemQuantity(
        productId: Int,
        quantity: Int
    ): Flow<DikshaResponse<Boolean>> {
        return flow {
            emit(DikshaResponse.Loading)
            val rowsAffected = cartDao.updateQuantity(productId, quantity)
            if (rowsAffected > 0) {
                emit(DikshaResponse.Success(true))
            } else {
                emit(DikshaResponse.Error("Failed to update cart item quantity."))
            }
        }
            .catch { emit(DikshaResponse.Error("Error updating cart item quantity.")) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun removeFromCart(cartItem: CartItem): Flow<DikshaResponse<Boolean>> {
        return flow {
            emit(DikshaResponse.Loading)
            val rowsDeleted = cartDao.delete(cartItem.toCartItemEntity())
            if (rowsDeleted > 0) {
                emit(DikshaResponse.Success(true))
            } else {
                emit(DikshaResponse.Error("Failed to remove item from cart."))
            }
        }
            .catch { emit(DikshaResponse.Error("Error removing item from cart.")) }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun deleteAllCart(): Int = cartDao.deleteAll()

}