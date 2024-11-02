package com.permana.dikshatechnicalassessment.core.stash.api

interface Stash {
    /**
     * Read value with [key] from preference.
     * [defaultValue] will be returned if there's no such [key] or invalid data type casting.
     */
    suspend fun <T> read(key: String, defaultValue: T): T

    /** Save [key]-[value] in preference. Return true if success. */
    suspend fun <T> save(key: String, value: T): Boolean

    /** Clear all entries in preference. Return true if success. */
    suspend fun clear(): Boolean

    /** Clear specific key in preference. Return true if success. */
    suspend fun remove(key: String): Boolean
}