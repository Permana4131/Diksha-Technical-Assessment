package com.permana.dikshatechnicalassessment.feature.login

import android.content.Intent
import android.view.View
import com.permana.dikshatechnicalassessment.core.component.BaseActivity
import com.permana.dikshatechnicalassessment.core.data.DikshaResponse
import com.permana.dikshatechnicalassessment.core.utils.extenstion.collectFlow
import com.permana.dikshatechnicalassessment.core.utils.extenstion.showToast
import com.permana.dikshatechnicalassessment.databinding.ActivityLoginBinding
import com.permana.dikshatechnicalassessment.feature.home.HomeActivity
import com.permana.xsisassessment.core.utils.extenstion.getLaunch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewmodel: LoginViewModel by viewModel()

    override fun setLayout(): View = binding.root

    override fun initView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)

        with(binding) {
            btnLogin.setOnClickListener {
                viewmodel.loginRequest.username = etUsername.text.toString().trim()
                viewmodel.loginRequest.password = etPassword.text.toString().trim()

                if (viewmodel.loginRequest.username.isEmpty()) {
                    etUsername.error = "Please enter your email"
                    etUsername.requestFocus()
                    return@setOnClickListener
                }

                if (viewmodel.loginRequest.password.isEmpty()) {
                    etPassword.error = "Please enter your password"
                    etPassword.requestFocus()
                    return@setOnClickListener
                }


                viewmodel.login()
            }
        }

        setFlowCollector()
    }

    private fun setFlowCollector() {
        collectFlow(viewmodel.login) { response ->
            when(response) {
                is DikshaResponse.Error -> {
                    loading(false)
                    showToast(response.message)
                }

                is DikshaResponse.Loading -> {
                    loading(true)
                }

                is DikshaResponse.Success -> {
                    getLaunch {
                        loading(false)
                        if (viewmodel.saveUserData(response.data.token)) {
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            showToast("Something went wrong pls try again")
                        }
                    }
                }

                else -> Unit
            }
        }
    }

    private fun loading(isShow: Boolean) {
        if (isShow) {
            binding.progressLoading.show()
            binding.ivLoading.visibility = View.VISIBLE
        } else {
            binding.progressLoading.hide()
            binding.ivLoading.visibility = View.GONE
        }
    }
}