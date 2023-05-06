package com.dhana.storio.ui.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dhana.storio.databinding.ActivityLoginBinding
import com.dhana.storio.ui.activity.HomeActivity
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        val job = lifecycleScope.launch {
            try {
                val result = viewModel.loginUser(email, password).single()
                if (result.isSuccess) {
                    // Login successful, navigate to home screen
                    showToast("Login Successful")
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Login failed, show error message
                    showToast("Login Failed: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                // Exception occurred, show error message
                showToast("Login Failed: ${e.message}")
            }
        }

        job.invokeOnCompletion { throwable ->
            if (throwable != null && !job.isCancelled) {
                // Exception occurred, show error message
                showToast("Login Failed: ${throwable.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

