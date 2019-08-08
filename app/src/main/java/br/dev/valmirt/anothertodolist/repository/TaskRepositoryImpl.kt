package br.dev.valmirt.anothertodolist.repository

import android.content.Context
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.db.TaskDao
import br.dev.valmirt.anothertodolist.model.Response
import br.dev.valmirt.anothertodolist.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class TaskRepositoryImpl: TaskRepository, KoinComponent {
    private val context: Context by inject()
    private val taskDao: TaskDao by inject()

    override suspend fun getAllTasks(): Response<List<Task>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val list = taskDao.findAll()

            if (list.isEmpty()) {
                Response<List<Task>>(emptyList(), context.getString(R.string.get_error))
            } else {
                Response<List<Task>>(list, "")
            }
        } catch (e: Exception) {
            Response<List<Task>>(emptyList(),
                e.message ?: context.getString(R.string.default_error))
        }
    }

    override suspend fun getTask(id: String): Response<Task?> = withContext(Dispatchers.IO) {
        return@withContext try {
            val task = taskDao.findById(id)

            if (task == null) {
                Response<Task?>(null, context.getString(R.string.get_error))
            } else {
                Response<Task?>(task, "")
            }
        } catch (e: Exception) {
            Response<Task?>(null,
                e.message ?: context.getString(R.string.default_error))
        }
    }

    override suspend fun saveTask(task: Task): Response<Task?> = withContext(Dispatchers.IO) {
        return@withContext try {
            taskDao.save(task)
            Response<Task?>(getTask(task.id).value, "")
        } catch (e: Exception) {
            Response<Task?>(null,
                e.message ?: context.getString(R.string.save_error))
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
        return@withContext when (getAllTasks().value.filter { it.isComplete }.size) {
            0 -> Response<Boolean>(false,  context.getString(R.string.default_error))
            else -> Response<Boolean>(true, "")
        }
    }

    override suspend fun deleteAllTasks(): Response<Boolean> = withContext(Dispatchers.IO) {
        taskDao.deleteAll()
        return@withContext when(getAllTasks().value.size) {
            0 -> Response<Boolean>(true,  "")
            else -> Response<Boolean>(false, context.getString(R.string.default_error))
        }
    }

    override suspend fun deleteTask(id: String): Response<Task?> = withContext(Dispatchers.IO) {
        return@withContext try {
            val task = getTask(id).value

            taskDao.deleteById(task?.id ?: "")
            Response<Task?>(task, "")
        } catch (e: Exception) {
            Response<Task?>(null, e.message ?: context.getString(R.string.default_error))
        }
    }

}