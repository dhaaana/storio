package com.dhana.storio.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dhana.storio.databinding.ActivitySplashBinding
import com.dhana.storio.ui.activity.home.HomeActivity
import com.dhana.storio.ui.activity.login.LoginActivity
import com.dhana.storio.ui.activity.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            lifecycleScope.launch {
                viewModel.isUserLoggedIn().collect { result ->
                    if (result) {
                        val moveToMainIntent =
                            Intent(this@SplashActivity, HomeActivity::class.java)
                        startActivity(moveToMainIntent)
                        finish()
                    } else {
                        val moveToMainIntent =
                            Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(moveToMainIntent)
                        finish()
                    }

                }
            }

        }, 2000)
    }
}