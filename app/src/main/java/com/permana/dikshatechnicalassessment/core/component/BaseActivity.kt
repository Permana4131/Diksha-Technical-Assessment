package com.permana.dikshatechnicalassessment.core.component

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun setLayout(): View
    abstract fun initView()
    open fun setViewModelObservable() {}

    open fun afterViewInit() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setContentView(setLayout())
        setViewModelObservable()
        afterViewInit()
    }
}
