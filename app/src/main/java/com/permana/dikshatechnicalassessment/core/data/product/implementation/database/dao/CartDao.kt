package com.permana.dikshatechnicalassessment.core.data.product.implementation.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.permana.dikshatechnicalassessment.core.data.product.implementation.database.entity.CartItemEntity

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItemEntity)

    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItems(): List<CartItemEntity>

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun findCartById(productId: Int): List<CartItemEntity>

    @Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
    suspend fun updateQuantity(productId: Int, quantity: Int): Int

    @Delete
    suspend fun delete(cartItem: CartItemEntity): Int

    @Query("DELETE FROM cart_items")
    suspend fun deleteAll(): Int
}