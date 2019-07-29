package br.dev.valmirt.anothertodolist.repository

import br.dev.valmirt.anothertodolist.db.TaskDao
import br.dev.valmirt.anothertodolist.model.Response
import br.dev.valmirt.anothertodolist.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class TaskRepositoryImpl (private val taskDao: TaskDao) : TaskRepository {
    override suspend fun getAllTasks(): Response<List<Task>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Response<List<Task>>(taskDao.findAll(), "")
        } catch (e: Exception) {
            Response<List<Task>>(emptyList(), e.message ?: "Something is wrong, try again later...")
        }
    }

    override suspend fun getTask(id: String): Response<Task?> = withContext(Dispatchers.IO) {
        return@withContext try {
            Response<Task?>(taskDao.findById(id), "")
        } catch (e: Exception) {
            Response<Task?>(null, e.message ?: "Something is wrong, try again later...")
        }
    }

    override suspend fun saveTask(task: Task): Response<Task?> = withContext(Dispatchers.IO) {
        return@withContext try {
            taskDao.save(task)
            Response<Task?>(getTask(task.id).value, "")
        } catch (e: Exception) {
            Response<Task?>(null, e.message ?: "Something is wrong, try again later...")
        }
    }

    override suspend fun completeTask(id: String): Response<Task?> = withContext(Dispatchers.IO) {
        taskDao.updateCompleted(id)
        return@withContext Response<Task?>(getTask(id).value, "")
    }

    override suspend fun activateTask(id: String): Response<Task?> = withContext(Dispatchers.IO){
        return@withContext completeTask(id)
    }

    override suspend fun clearCompletedTasks(): Response<Boolean> = withContext(Dispatchers.IO) {
        taskDao.deleteAllCompleted()
        return@withContext when (getAllTasks().value.size) {
            0 -> Response<Boolean>(false,  "Something is wrong, try again later...")
            else -> Response<Boolean>(true, "")
        }
    }

    override suspend fun deleteAllTasks(): Response<Boolean> = withContext(Dispatchers.IO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteTask(id: String): Response<Task?> = withContext(Dispatchers.IO) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}