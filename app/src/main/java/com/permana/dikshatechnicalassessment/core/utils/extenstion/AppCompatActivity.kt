package com.permana.dikshatechnicalassessment.core.utils.extenstion

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.permana.xsisassessment.core.utils.extenstion.getLaunch
import kotlinx.coroutines.flow.Flow

fun <T> AppCompatActivity.collectFlow(flow: Flow<T>, action: ((T) -> Unit)){
    getLaunch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collect{
                action.invoke(it)
            }
        }
    }
}