package br.dev.valmirt.anothertodolist.repository

import br.dev.valmirt.anothertodolist.model.Response
import br.dev.valmirt.anothertodolist.model.Task

interface TaskRepository {

    suspend fun getAllTasks () : Response<List<Task>>

    suspend fun getTask (id: String) : Response<Task?>

    suspend fun saveTask (task: Task) : Response<Task?>

    suspend fun completeTask (id: String) : Response<Task?>

    suspend fun activateTask (id: String) : Response<Task?>

    suspend fun clearCompletedTasks () : Response<Boolean>

    suspend fun deleteAllTasks () : Response<Boolean>

    suspend fun deleteTask (id: String) : Response<Task?>
}