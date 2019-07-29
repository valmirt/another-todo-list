package br.dev.valmirt.anothertodolist.repository

import br.dev.valmirt.anothertodolist.model.Response
import br.dev.valmirt.anothertodolist.model.Task

class FakeRepository : TaskRepository {
    private val fakeDbUtil = mutableMapOf<String, Task>()
    private var shouldReturnError = false

    fun setShouldReturnError() {
        shouldReturnError = !shouldReturnError
    }

    override suspend fun getAllTasks(): Response<List<Task>> {
        return if (shouldReturnError) {
            Response<List<Task>>(emptyList(), "Exception forced!")
        } else {
            Response<List<Task>>(fakeDbUtil.values.toList(), "")
        }
    }

    override suspend fun getTask(id: String): Response<Task?> {
        return if (shouldReturnError) {
            Response<Task?>(null, "Exception forced!")
        } else {
            fakeDbUtil[id]?.let {
                Response<Task?>(it, "")
            }
            Response<Task?>(null, "Error not found")
        }
    }

    override suspend fun saveTask(task: Task): Response<Task?> {
        return if (shouldReturnError) {
            Response<Task?>(null, "Exception forced!")
        } else {
            fakeDbUtil[task.id] = task
            Response<Task?>(task, "")
        }
    }

    override suspend fun completeTask(id: String): Response<Task?> {
        return if (shouldReturnError) {
            Response<Task?>(null, "Exception forced!")
        } else {
            fakeDbUtil[id]?.let {
                Response<Task?>(Task(
                    it.id,
                    it.title,
                    it.description,
                    it.date,
                    true), "")
            }
            Response<Task?>(null, "Error not found")
        }
    }

    override suspend fun activateTask(id: String): Response<Task?> {
        return if (shouldReturnError) {
            Response<Task?>(null, "Exception forced!")
        } else {
            fakeDbUtil[id]?.let {
                Response<Task?>(Task(
                    it.id,
                    it.title,
                    it.description,
                    it.date,
                    false), "")
            }
            Response<Task?>(null, "Error not found")
        }
    }

    override suspend fun clearCompletedTasks(): Response<Boolean> {
        return if (shouldReturnError) {
            Response<Boolean>(false, "Exception forced!")
        } else {
            fakeDbUtil.forEach { (k, v) ->
                if (v.isComplete) fakeDbUtil.remove(k)
            }
            Response<Boolean>(true, "")
        }
    }

    override suspend fun deleteAllTasks(): Response<Boolean> {
        return if (shouldReturnError) {
            Response<Boolean>(false, "Exception forced!")
        } else {
            fakeDbUtil.forEach { (k, _) ->
                fakeDbUtil.remove(k)
            }
            Response<Boolean>(true, "")
        }
    }

    override suspend fun deleteTask(id: String): Response<Task?> {
        return if (shouldReturnError) {
            Response<Task?>(null, "Exception forced!")
        } else {
            fakeDbUtil[id]?.let {
                fakeDbUtil.remove(it.id)
                Response<Task?>(it, "")
            }
            Response<Task?>(null, "Error not found")
        }
    }
}