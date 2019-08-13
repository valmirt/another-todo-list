package br.dev.valmirt.anothertodolist.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import br.dev.valmirt.anothertodolist.utils.Constants
import org.koin.dsl.module

val cacheModule = module {
    single<SharedPreferences> {
        get<Context>().getSharedPreferences(Constants.PROFILE, Application.MODE_PRIVATE)
    }
}