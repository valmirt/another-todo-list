package br.dev.valmirt.anothertodolist.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import br.dev.valmirt.anothertodolist.R

class SplashActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.setHomeActivity(this)
    }
}
