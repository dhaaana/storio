package com.dhana.storio.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dhana.storio.R
import com.dhana.storio.databinding.ActivityLoginBinding
import com.dhana.storio.ui.activity.home.HomeActivity
import com.dhana.storio.ui.activity.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        playAnimation()

        binding.toRegisterButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        lifecycleScope.launch {
            binding.loginButton.isEnabled = false
            binding.loginButton.setText(R.string.loading_text)
            try {
                viewModel.loginUser(email, password).collect { result ->
                    if (result.isSuccess) {
                        binding.loginButton.isEnabled = true
                        binding.loginButton.setText(R.string.login_button_text)
                        showToast("Login Successful")
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        binding.loginButton.isEnabled = true
                        binding.loginButton.setText(R.string.login_button_text)
                        showToast("Login Failed: ${result.exceptionOrNull()?.message}")
                    }
                }
            } catch (e: Exception) {
                // Exception occurred, show error message
                binding.loginButton.isEnabled = true
                binding.loginButton.setText(R.string.login_button_text)
                showToast("Login Failed: ${e.message}")
            }
        }
    }

    private fun playAnimation() {
        binding.appName.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.edLoginEmail.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.edLoginPassword.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.loginButton.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.toRegisterButton.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.tvRegisterHint.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
