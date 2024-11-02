package com.permana.dikshatechnicalassessment.core.data.product.api.model

data class CartItem (
    val id: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    val quantity: Int = 1,
    val imageUrl: String? = null
)