package br.dev.valmirt.anothertodolist.di

import br.dev.valmirt.anothertodolist.repository.TaskRepository
import br.dev.valmirt.anothertodolist.repository.TaskRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single <TaskRepository> {
        TaskRepositoryImpl()
    }
}