package br.dev.valmirt.anothertodolist.di

import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.repository.FakeRepository
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import org.koin.dsl.module

val fakeRepo = module {
    single<TaskRepository> {
        val fakeDb = mutableMapOf<String, Task>()
        val task1 = Task(title = "Buy Some Eggs", description = "I love it")
        fakeDb[task1.id] = task1
        val task2 = Task(title = "Walk to the dog", description = "", isComplete = true)
        fakeDb[task2.id] = task2
        val task3 = Task(title = "Learn Android unit test", description = "", isComplete = true)
        fakeDb[task3.id] = task3
        val task4 = Task(title = "Meditate", description = "It's nice!")
        fakeDb[task4.id] = task4
        FakeRepository(fakeDb)
    }
}