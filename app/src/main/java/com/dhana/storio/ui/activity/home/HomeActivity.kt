package com.dhana.storio.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhana.storio.data.remote.response.Story
import com.dhana.storio.databinding.ActivityHomeBinding
import com.dhana.storio.ui.activity.add.AddStoryActivity
import com.dhana.storio.ui.activity.detail.DetailActivity
import com.dhana.storio.ui.activity.register.RegisterActivity
import com.dhana.storio.ui.adapter.StoryListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.getUserToken().collect { token ->
                if (token !== null) {
                    val bearerToken = "Bearer $token"
                    viewModel.getAllStories(
                        1,
                        10,
                        0,
                        bearerToken
                    ).collectLatest { result ->
                        if (result.isSuccess) {
                            val storiesResponse = result.getOrThrow()
                            setStoryListData(storiesResponse.stories)
                        } else {
                            showToast("Home Failed: ${result.exceptionOrNull()?.message}")
                        }
                    }
                } else {
                    showToast("Home Failed: No Token")
                }
            }
        }

        binding.btnAddStory.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setStoryListData(storyList: List<Story>?) {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager

        val adapter = StoryListAdapter(storyList ?: emptyList())
        binding.rvStory.adapter = adapter
        adapter.setOnItemClickCallback(object : StoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Story) {
                val detailIntent = Intent(this@HomeActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_STORY_ID, data.id)
                startActivity(detailIntent)
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
