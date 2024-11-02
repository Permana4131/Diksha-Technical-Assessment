package com.permana.dikshatechnicalassessment.core.data

sealed interface DikshaResponse<out T> {
    object Loading : DikshaResponse<Nothing>

    open class Error(
        open val message: String,
        val meta: Map<String, Any?> = mapOf(),
    ) : DikshaResponse<Nothing>

    object Empty : DikshaResponse<Nothing>

    class Success<T>(
        val data: T,
        val meta: Map<String, Any?> = mapOf(),
    ) : DikshaResponse<T>
}