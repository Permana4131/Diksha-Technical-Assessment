package com.permana.dikshatechnicalassessment.core.stash.implementation

import android.content.SharedPreferences
import com.permana.dikshatechnicalassessment.core.stash.api.Stash
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class StashImpl(
    private val sharedPreferences: SharedPreferences,
    private val ioDispatcher: CoroutineDispatcher,
) : Stash {

    override suspend fun <T> read(key: String, defaultValue: T): T {
        return withContext(context = ioDispatcher) {
            try {
                when (defaultValue) {
                    is Int -> sharedPreferences.getInt(key, defaultValue)
                    is Long -> sharedPreferences.getLong(key, defaultValue)
                    is Double -> sharedPreferences.getFloat(key, defaultValue.toFloat()).toDouble()
                    is Boolean -> sharedPreferences.getBoolean(key, defaultValue)
                    is String -> sharedPreferences.getString(key, defaultValue)
                    else -> throw UnsupportedOperationException("Data type not supported.")
                } as T
            } catch (e: Exception) {
                defaultValue
            }
        }
    }

    override suspend fun <T> save(key: String, value: T): Boolean {
        return withContext(context = ioDispatcher) {
            try {
                val editor = sharedPreferences.edit()
                when (value) {
                    is Int -> editor.putInt(key, value)
                    is Long -> editor.putLong(key, value)
                    is Double -> editor.putFloat(key, value.toFloat())
                    is Boolean -> editor.putBoolean(key, value)
                    is String -> editor.putString(key, value)
                    else -> throw UnsupportedOperationException("Data type not supported.")
                }
                editor.commit()
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun clear(): Boolean {
        return withContext(context = ioDispatcher) {
            try {
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.commit()
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun remove(key: String): Boolean {
        return withContext(context = ioDispatcher) {
            try {
                sharedPreferences.edit().remove(key).commit()
            } catch (e: Exception) {
                false
            }
        }
    }

}
