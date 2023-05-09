package com.dhana.storio.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhana.storio.R
import com.dhana.storio.data.remote.response.Story
import com.dhana.storio.databinding.ActivityHomeBinding
import com.dhana.storio.ui.activity.add.AddStoryActivity
import com.dhana.storio.ui.activity.detail.DetailActivity
import com.dhana.storio.ui.activity.login.LoginActivity
import com.dhana.storio.ui.activity.maps.MapsActivity
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
                }
            }
        }

        binding.btnAddStory.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddStoryActivity::class.java)
            startActivity(intent)
        }

        binding.btnMapPage.setOnClickListener {
            val intent = Intent(this@HomeActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_button -> {
                logOutHandler()
            }
        }
        return super.onOptionsItemSelected(item)
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

    private fun logOutHandler() {
        lifecycleScope.launch {
            viewModel.logOut().collect { result ->
                if (result.isSuccess) {
                    showToast("Logout Successful")
                    val intent = Intent(this@HomeActivity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                } else {
                    showToast("Logout Failed: ${result.exceptionOrNull()?.message}")
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
