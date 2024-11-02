package com.permana.dikshatechnicalassessment.core.data.product.implementation.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val title: String,
    val price: Double,
    val quantity: Int = 1,
    val imageUrl: String? = null
)