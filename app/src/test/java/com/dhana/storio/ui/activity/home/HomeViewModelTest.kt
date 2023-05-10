package com.dhana.storio.ui.activity.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.dhana.storio.MainDispatcherRule
import com.dhana.storio.data.StoryRepository
import com.dhana.storio.data.UserRepository
import com.dhana.storio.data.remote.response.Story
import com.dhana.storio.ui.adapter.StoryListAdapter
import com.dhana.storio.utils.StoryPagingSourceTestUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import com.dhana.storio.utils.getOrAwaitValue

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @Test
    fun `getAllStories() function should not return null, return the actual size, and the first data that returned is correct`() =
        runTest {
            val dummyStory = listOf(
                Story(
                    "1",
                    "Dhana",
                    "A tale of two friends who embark on a journey to the unknown.",
                    "https://source.unsplash.com/random/300x300/?nature",
                    "2021-01-01",
                    4.5,
                    20.0
                ),
                Story(
                    "2",
                    "Dhani",
                    "A tale of two friends who fall in love.",
                    "https://source.unsplash.com/random/300x300/?love",
                    "2021-01-01",
                    4.5,
                    20.0
                ),
            )
            val data: PagingData<Story> = StoryPagingSourceTestUtils.createSnapshot(dummyStory)
            val expectedStory = MutableLiveData<PagingData<Story>>()
            expectedStory.value = data

            `when`(storyRepository.getAllStories("Dummy Token")).thenReturn(expectedStory)
            val homeViewModel = HomeViewModel(storyRepository, userRepository)
            val actualStory: PagingData<Story> = homeViewModel.getAllStories("Dummy Token").getOrAwaitValue()

            val differ = AsyncPagingDataDiffer(
                diffCallback = StoryListAdapter.DIFF_CALLBACK,
                updateCallback = noopListUpdateCallback,
                workerDispatcher = Dispatchers.Main,
            )
            differ.submitData(actualStory)

            Assert.assertNotNull(differ.snapshot())
            Assert.assertEquals(dummyStory.size, differ.snapshot().size)
            Assert.assertEquals(dummyStory[0], differ.snapshot()[0])
        }

    @Test
    fun `getAllStories() function should return zero data when returned data is empty`() = runTest {
        val data: PagingData<Story> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<Story>>()
        expectedStory.value = data

        `when`(storyRepository.getAllStories("Dummy Token")).thenReturn(expectedStory)
        val homeViewModel = HomeViewModel(storyRepository, userRepository)
        val actualStory: PagingData<Story> = homeViewModel.getAllStories("Dummy Token").getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryListAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )

        differ.submitData(actualStory)
        Assert.assertEquals(0, differ.snapshot().size)
    }

    companion object {
        val noopListUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}
            override fun onRemoved(position: Int, count: Int) {}
            override fun onMoved(fromPosition: Int, toPosition: Int) {}
            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }
    }
}



