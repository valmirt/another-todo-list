package br.dev.valmirt.anothertodolist.di

import androidx.room.Room
import br.dev.valmirt.anothertodolist.db.ToDoDatabase
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import br.dev.valmirt.anothertodolist.repository.TaskRepositoryImpl
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(get(),
            ToDoDatabase::class.java, "todo.db")
            .build()
    }

    single { get<ToDoDatabase>().taskDao() }
}

val repositoryModule = module {
    single {
        TaskRepositoryImpl(get(), get())
    }
}