package com.permana.dikshatechnicalassessment.core.data.product.implementation.mapper

import com.permana.dikshatechnicalassessment.core.data.product.api.model.ProductsCategories

fun List<String>?.toProductsCategories() = ProductsCategories(
    categories = this ?: listOf()
)