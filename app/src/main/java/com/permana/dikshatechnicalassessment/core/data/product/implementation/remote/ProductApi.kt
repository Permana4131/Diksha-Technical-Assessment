package com.permana.dikshatechnicalassessment.core.data.product.implementation.remote

import com.permana.dikshatechnicalassessment.core.data.product.implementation.response.ProductResponseItem
import retrofit2.http.GET
import retrofit2.http.Path


interface ProductApi {
    @GET("products/categories")
    suspend fun fetchCategories(): List<String>?

    @GET("products")
    suspend fun fetchProducts(): List<ProductResponseItem>?

    @GET("products/category/{categories}")
    suspend fun fetchProductsByCategories(
        @Path("categories") categories: String,
    ): List<ProductResponseItem>?

    @GET("products/{id}")
    suspend fun fetchSingleProduct(
        @Path("id") id: Int,
    ): ProductResponseItem
}