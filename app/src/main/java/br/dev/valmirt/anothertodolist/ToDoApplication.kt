package br.dev.valmirt.anothertodolist

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import br.dev.valmirt.anothertodolist.di.dbModule
import br.dev.valmirt.anothertodolist.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ToDoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ToDoApplication)
            modules(listOf(dbModule, repositoryModule))
        }

//        TODO:Set the dark theme with shared preferences in settings
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}