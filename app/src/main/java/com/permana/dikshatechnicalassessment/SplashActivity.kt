package com.permana.dikshatechnicalassessment

import android.content.Intent
import android.view.View
import com.permana.dikshatechnicalassessment.core.component.BaseActivity
import com.permana.dikshatechnicalassessment.databinding.ActivitySplashBinding
import com.permana.dikshatechnicalassessment.feature.home.HomeActivity
import com.permana.dikshatechnicalassessment.feature.login.LoginActivity
import com.permana.dikshatechnicalassessment.feature.login.LoginViewModel
import com.permana.xsisassessment.core.utils.extenstion.getLaunch
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity: BaseActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val viewmodel: LoginViewModel by viewModel()

    override fun setLayout(): View = binding.root

    override fun initView() {
        binding = ActivitySplashBinding.inflate(layoutInflater)

        getLaunch {
            delay(2000)
            viewmodel.readUserData().let {
                if (it.username.isNotEmpty()) {
                    startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }

                finish()
            }
        }

    }
}