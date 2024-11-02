package com.permana.dikshatechnicalassessment.core.data.product.implementation.mapper

import com.permana.dikshatechnicalassessment.core.data.product.api.model.CartItem
import com.permana.dikshatechnicalassessment.core.data.product.implementation.database.entity.CartItemEntity

fun CartItemEntity.toCartItem() = CartItem (
    id = this.id,
    productId = this.productId,
    title = this.title,
    price = this.price,
    quantity = this.quantity,
    imageUrl = this.imageUrl,
)

fun CartItem.toCartItemEntity() = CartItemEntity (
    id = this.id,
    productId = this.productId,
    title = this.title,
    price = this.price,
    quantity = this.quantity,
    imageUrl = this.imageUrl,
)