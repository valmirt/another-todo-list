package br.dev.valmirt.anothertodolist

import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import br.dev.valmirt.anothertodolist.di.dbModule
import br.dev.valmirt.anothertodolist.di.repositoryModule
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.DARK_THEME
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.SELECTED_THEME
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication: Application() {

    companion object {

        fun selectedDarkTheme (preference: Boolean) {
            if (preference) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        private fun isAtLeastP() = Build.VERSION.SDK_INT >= 28
        private fun isAtLeastL() = Build.VERSION.SDK_INT >= 21
    }

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

        selectedDarkTheme(preference)
    }
}