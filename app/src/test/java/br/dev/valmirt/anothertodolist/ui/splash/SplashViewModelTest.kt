package br.dev.valmirt.anothertodolist.ui.splash

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class SplashViewModelTest {

    private lateinit var viewModel: SplashViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUpViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = SplashViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun delayTransitionTest() = runBlocking {
        var text = ""
        val start = System.currentTimeMillis()
        launch(Dispatchers.Main) {
            println("teste")
            viewModel.delayToNextScreen {
                text = "This block is invoked after: ${System.currentTimeMillis() - start} ms."
            }
        }


        assertThat(text, `is`("This block is invoked after: 1500 ms."))
    }
}