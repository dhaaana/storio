package com.dhana.storio.ui.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dhana.storio.databinding.ActivityRegisterBinding
import com.dhana.storio.ui.activity.login.LoginActivity
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            register(name, email, password)
        }
    }

    private fun register(name:String, email: String, password: String) {
        val job = lifecycleScope.launch {
            try {
                val result = viewModel.registerUser(name, email, password).single()
                if (result.isSuccess) {
                    // Register successful, navigate to home screen
                    showToast("Register Successful")
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Register failed, show error message
                    showToast("Register Failed: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                // Exception occurred, show error message
                showToast("Register Failed: ${e.message}")
            }
        }

        job.invokeOnCompletion { throwable ->
            if (throwable != null && !job.isCancelled) {
                // Exception occurred, show error message
                showToast("Register Failed: ${throwable.message}")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

