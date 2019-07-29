package br.dev.valmirt.anothertodolist.ui.splash

import android.content.Intent
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import br.dev.valmirt.anothertodolist.ui.home.HomeActivity


class SplashViewModel : ViewModel() {

    fun setHomeActivity (activity: AppCompatActivity) {
        val r = Runnable {
            val intent = Intent(activity, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            activity.startActivity(intent)
            activity.finish()
        }

        Handler().postDelayed(r, 1000)
    }
}