package com.permana.dikshatechnicalassessment.core.data.product.implementation.mapper

import com.permana.dikshatechnicalassessment.core.data.product.api.model.CartItem
import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductItem
import com.permana.dikshatechnicalassessment.core.data.product.api.model.Rating
import com.permana.dikshatechnicalassessment.core.data.product.implementation.response.ProductResponseItem
import com.permana.dikshatechnicalassessment.core.data.product.implementation.response.RatingResponse

fun ProductResponseItem.toProductItem() = ProductItem (
    image = this.image ?: "",
    price = this.price ?: 0.0,
    rating = this.rating.toRating(),
    description = this.description ?: "",
    id = this.id ?: 0,
    title = this.title ?: "",
    category = this.category ?: "",
)

fun RatingResponse?.toRating() = Rating (
    rate = this?.rate ?: 0.0,
    count = this?.count ?: 0,
)


fun ProductItem.toCartItem() = CartItem(
    productId = this.id,
    title = this.title,
    price = this.price,
    imageUrl = this.image,
)