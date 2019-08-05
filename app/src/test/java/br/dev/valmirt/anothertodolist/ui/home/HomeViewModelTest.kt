package br.dev.valmirt.anothertodolist.ui.home

import br.dev.valmirt.anothertodolist.di.fakeRepo
import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.repository.FakeRepository
import br.dev.valmirt.anothertodolist.utils.LiveDataTestUtil
import br.dev.valmirt.anothertodolist.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
class HomeViewModelTest: KoinTest {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp () {
        startKoin {
            modules(fakeRepo)
        }

        viewModel = HomeViewModel()
    }

    @Test
    fun getAllTasksTest() = runBlocking {
//        // Pause dispatcher so we can verify initial values
//        mainCoroutineRule.pauseDispatcher()
//
//        viewModel.updateTaskList()
//        // Then progress indicator is shown
//        assertThat(LiveDataTestUtil.getValue(viewModel.spinner), `is`(true))
//
//        // Execute pending coroutines actions
//        mainCoroutineRule.resumeDispatcher()
//
//        // Then progress indicator is hidden
//        assertThat(viewModel.spinner.value, `is`(false))
//
//        // And data correctly loaded
//        assertThat(viewModel.tasks.value?.size, `is`(4))
    }
}