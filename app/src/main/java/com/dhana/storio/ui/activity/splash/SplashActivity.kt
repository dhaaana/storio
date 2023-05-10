package com.dhana.storio.ui.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dhana.storio.databinding.ActivitySplashBinding
import com.dhana.storio.ui.activity.home.HomeActivity
import com.dhana.storio.ui.activity.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
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