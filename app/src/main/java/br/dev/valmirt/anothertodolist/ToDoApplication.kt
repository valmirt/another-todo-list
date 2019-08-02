package br.dev.valmirt.anothertodolist

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import br.dev.valmirt.anothertodolist.di.dbModule
import br.dev.valmirt.anothertodolist.di.repositoryModule
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.DARK_THEME
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.SELECTED_THEME
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication: Application() {

    private val preferences: SharedPreferences by lazy {
        getSharedPreferences(DARK_THEME, MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ToDoApplication)
            modules(listOf(dbModule, repositoryModule))
        }

        val preference = preferences.getBoolean(SELECTED_THEME,  false)

        if (preference) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }

    }
}