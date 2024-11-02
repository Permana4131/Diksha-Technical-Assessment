package com.permana.dikshatechnicalassessment.core.data.product.api.model

data class ProductItem(
	val image: String,
	val price: Double,
	val rating: Rating,
	val description: String,
	val id: Int,
	val title: String,
	val category: String,
)

data class Rating(
	val rate: Double,
	val count: Int,
)

