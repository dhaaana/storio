package com.dhana.storio.ui.activity.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dhana.storio.data.remote.response.Story
import com.dhana.storio.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyId = intent.getStringExtra(EXTRA_STORY_ID)

        lifecycleScope.launch {
            viewModel.getUserToken().collect { token ->
                if (token !== null && storyId !== null) {
                    val bearerToken = "Bearer $token"
                    viewModel.getStoryDetail(storyId, bearerToken).collect { result ->
                        if (result.isSuccess) {
                            val storyDetail = result.getOrThrow()
                            setStoryDetail(storyDetail.story)
                        } else {
                            showToast("Detail Failed: ${result.exceptionOrNull()?.message}")
                        }
                    }
                } else {
                    showToast("Detail Failed: No Token")
                }
            }

        }
    }

    private fun setStoryDetail(storyDetail: Story) {
        Glide.with(this)
            .load(storyDetail.photoUrl)
            .into(binding.ivDetailPhoto)
        binding.tvDetailName.text = storyDetail.name
        binding.tvDetailDescription.text = storyDetail.description
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_STORY_ID = "EXTRA_STORY_ID"
    }
}
