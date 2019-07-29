package br.dev.valmirt.anothertodolist

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class ToDoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

//        TODO:Set the dark theme with shared preferences in settings
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}