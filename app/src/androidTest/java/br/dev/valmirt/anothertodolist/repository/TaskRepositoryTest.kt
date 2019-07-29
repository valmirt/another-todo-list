package br.dev.valmirt.anothertodolist.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.db.ToDoDatabase
import br.dev.valmirt.anothertodolist.model.Task
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskRepositoryTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var db: ToDoDatabase
    private lateinit var context: Context

    @Before
    fun createDb() {
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ToDoDatabase::class.java).build()

        taskRepository = TaskRepositoryImpl(context, db.taskDao())
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    @Test
    fun writeAndReadTaskTest () = runBlocking {
        val task = Task(
            title = "Test 1",
            description = "This is a save test")

        taskRepository.saveTask(task)
        assertThat(taskRepository
            .getTask(task.id)
            .value?.title,
            `is`(task.title)
        )
    }

    @Test
    fun getAllTasksTest () = runBlocking {
        val myList = mutableListOf<Task>()
        myList.add(Task(title = "Test 1", description = ""))
        myList.add(Task(title = "Test 2", description = ""))
        myList.add(Task(title = "Test 3", description = ""))

        myList.forEach {
            taskRepository.saveTask(it)
        }

        assertThat(taskRepository
            .getAllTasks().value.size,
            `is`(myList.size)
        )
    }

    @Test
    fun deleteAllTasksTest () = runBlocking {
        val myList = mutableListOf<Task>()
        myList.add(Task(title = "Test 1", description = ""))
        myList.add(Task(title = "Test 2", description = ""))
        myList.add(Task(title = "Test 3", description = ""))

        myList.forEach {
            taskRepository.saveTask(it)
        }
        taskRepository.deleteAllTasks()
        assertThat(taskRepository
            .getAllTasks().value.size,
            `is`(0)
        )
    }

    @Test
    fun updateCompleteTaskTest () = runBlocking {
        val task = Task(
            title = "Test 1",
            description = "This is a save test")

        taskRepository.saveTask(task)

        taskRepository.completeTask(task.id)

        assertThat(taskRepository
            .getTask(task.id).value?.isComplete,
            `is`(true)
        )
    }

    @Test
    fun writeAndDeleteTaskTest () = runBlocking {
        val task = Task(
            title = "Test 1",
            description = "This is a save test")

        taskRepository.saveTask(task)
        assertThat(taskRepository
            .getTask(task.id).value?.title,
            `is`(task.title)
        )

        taskRepository.deleteTask(task.id)
        assertThat(taskRepository
            .getAllTasks().value.size,
            `is`(0)
        )
    }

    @Test
    fun deleteAllCompleteTasksTest () = runBlocking {
        val myList = mutableListOf<Task>()
        myList.add(Task(title = "Test 1", description = ""))
        myList.add(Task(title = "Test 2", description = ""))
        myList.add(Task(title = "Test 3", description = ""))

        myList.forEach {
            taskRepository.saveTask(it)
        }

        val tasksDb = taskRepository.getAllTasks().value
        val task1 = tasksDb[0]
        val task2 = tasksDb[1]

        taskRepository.completeTask(task1.id)
        taskRepository.completeTask(task2.id)

        taskRepository.clearCompletedTasks()
        assertThat(taskRepository
            .getAllTasks().value.size,
            `is`(1)
        )
    }

    @Test
    fun catchErrorOperations () = runBlocking {
        val task = Task(
            title = "Test 1",
            description = "This is a save test")

        taskRepository.saveTask(task)
        val result = taskRepository.getTask("errorId")
        assertThat(result.message, `is`(context.getString(R.string.get_error)))
    }
}