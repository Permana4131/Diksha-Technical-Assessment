package com.permana.dikshatechnicalassessment.core.data.product.implementation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.permana.dikshatechnicalassessment.core.data.product.implementation.database.dao.CartDao
import com.permana.dikshatechnicalassessment.core.data.product.implementation.database.entity.CartItemEntity

@Database(
    entities = [
        CartItemEntity::class,
    ],
    version = 1,
)
abstract class ProductDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private const val DB_NAME = "product.db"

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): ProductDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    ctx.applicationContext,
                    ProductDatabase::class.java,
                    DB_NAME
                )
                    .build()
            }
            return INSTANCE as ProductDatabase
        }
    }
}