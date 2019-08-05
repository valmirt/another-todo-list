package br.dev.valmirt.anothertodolist.ui.splash

import br.dev.valmirt.anothertodolist.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUpViewModel() {
        viewModel = SplashViewModel()
    }

    @Test
    fun delayTransitionTest() {
        var text = ""

        viewModel.delayToNextScreen {
            text = "This block is really invoked! :)"
        }

        mainCoroutineRule.resumeDispatcher()

        assertThat(text, `is`("This block is really invoked! :)"))
    }
}