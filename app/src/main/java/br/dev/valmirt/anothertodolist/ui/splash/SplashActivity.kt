package br.dev.valmirt.anothertodolist.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import br.dev.valmirt.anothertodolist.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorSplash)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.my_fragment).navigateUp()

    override fun onPause() {
        super.onPause()
        finish()
    }
}
