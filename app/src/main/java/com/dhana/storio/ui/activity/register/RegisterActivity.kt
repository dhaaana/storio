package com.dhana.storio.ui.activity.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dhana.storio.R
import com.dhana.storio.databinding.ActivityRegisterBinding
import com.dhana.storio.ui.activity.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        playAnimation()

        binding.toLoginButton.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            register(name, email, password)
        }
    }

    private fun register(name:String, email: String, password: String) {
        lifecycleScope.launch {
            binding.registerButton.isEnabled = false
            binding.registerButton.setText(R.string.loading_text)
            try {
                val result = viewModel.registerUser(name, email, password).single()
                if (result.isSuccess) {
                    binding.registerButton.isEnabled = true
                    binding.registerButton.setText(R.string.register_button_text)
                    showToast("Register Successful")
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    binding.registerButton.isEnabled = true
                    binding.registerButton.setText(R.string.register_button_text)
                    showToast("Register Failed: ${result.exceptionOrNull()?.message}")
                }
            } catch (e: Exception) {
                binding.registerButton.isEnabled = true
                binding.registerButton.setText(R.string.register_button_text)
                showToast("Register Failed: ${e.message}")
            }
        }
    }

    private fun playAnimation() {
        binding.appName.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.edRegisterName.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.edRegisterEmail.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.edRegisterPassword.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.registerButton.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.toLoginButton.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }

        binding.tvLoginHint.apply {
            alpha = 0f
            animate().alpha(1f).duration = 500
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

