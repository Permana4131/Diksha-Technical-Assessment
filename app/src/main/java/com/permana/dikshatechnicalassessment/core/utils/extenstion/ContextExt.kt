package com.permana.dikshatechnicalassessment.core.utils.extenstion

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

fun Context.showToast(message: String) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    } else {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}