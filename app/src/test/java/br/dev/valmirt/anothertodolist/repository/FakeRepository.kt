package br.dev.valmirt.anothertodolist.repository

import br.dev.valmirt.anothertodolist.model.Response
import br.dev.valmirt.anothertodolist.model.Task

class FakeRepository(private val fakeDbUtil: MutableMap<String, Task>) : TaskRepository {

    override suspend fun getAllTasks(): Response<List<Task>>
            = Response<List<Task>>(fakeDbUtil.values.toList(), "")


    override suspend fun getTask(id: String): Response<Task?> {
        fakeDbUtil[id]?.let {
            return Response<Task?>(it, "")
        }
        return Response<Task?>(null, "Error not found")
    }

    override suspend fun saveTask(task: Task): Response<Task?> {
        fakeDbUtil[task.id] = task
        return Response<Task?>(task, "")
    }

    override suspend fun completeTask(id: String): Response<Task?> {
        fakeDbUtil[id]?.let {
            return Response<Task?>(Task(
                it.id,
                it.title,
                it.description,
                it.date,
                true), "")
        }
        return Response<Task?>(null, "Error not found")
    }

    override suspend fun activateTask(id: String): Response<Task?> {
        fakeDbUtil[id]?.let {
            return Response<Task?>(Task(
                it.id,
                it.title,
                it.description,
                it.date,
                false), "")
        }
        return Response<Task?>(null, "Error not found")
    }

    override suspend fun clearCompletedTasks(): Response<Boolean> {
        fakeDbUtil.forEach { (k, v) ->
            if (v.isComplete) fakeDbUtil.remove(k)
        }
        return Response<Boolean>(true, "")
    }

    override suspend fun deleteAllTasks(): Response<Boolean> {
        fakeDbUtil.forEach { (k, _) ->
            fakeDbUtil.remove(k)
        }
        return Response<Boolean>(true, "")
    }

    override suspend fun deleteTask(id: String): Response<Task?> {
        fakeDbUtil[id]?.let {
            fakeDbUtil.remove(it.id)
            return Response<Task?>(it, "")
        }
        return Response<Task?>(null, "Error not found")
    }
}