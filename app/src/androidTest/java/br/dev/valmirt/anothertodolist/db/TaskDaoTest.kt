package br.dev.valmirt.anothertodolist.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import br.dev.valmirt.anothertodolist.model.Task
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class TaskDaoTest {

    private lateinit var taskDao: TaskDao
    private lateinit var db: ToDoDatabase

    @Before
    fun createDb () {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ToDoDatabase::class.java).build()

        taskDao = db.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb () = db.close()

    @Test
    fun writeAndReadTaskTest () = runBlocking {
        val task = Task(
            title = "Test 1",
            description = "This is a save test")

        taskDao.save(task)
        assertThat(taskDao.findById(task.id)?.title, `is`(task.title))
    }

    @Test
    fun updateTask () = runBlocking {
        val task = Task(
            title = "Test 1",
            description = "This is a save test")

        taskDao.save(task)

        val task2 = Task(task.id, "Test Updated", task.description)

        taskDao.update(task2)

        assertThat(taskDao.findById(task.id)?.title, `is`(task2.title))
    }

    @Test
    fun getAllTasksTest () = runBlocking {
        val myList = mutableListOf<Task>()
        myList.add(Task(title = "Test 1", description = ""))
        myList.add(Task(title = "Test 2", description = ""))
        myList.add(Task(title = "Test 3", description = ""))

        myList.forEach {
            taskDao.save(it)
        }

        assertThat(taskDao.findAll().size, `is`(myList.size))
    }

    @Test
    fun deleteAllTasksTest () = runBlocking {
        val myList = mutableListOf<Task>()
        myList.add(Task(title = "Test 1", description = ""))
        myList.add(Task(title = "Test 2", description = ""))
        myList.add(Task(title = "Test 3", description = ""))

        myList.forEach {
            taskDao.save(it)
        }
        taskDao.deleteAll()
        assertThat(taskDao.findAll().size, `is`(0))
    }

    @Test
    fun updateCompleteTaskTest () = runBlocking {
        val task = Task(
            title = "Test 1",
            description = "This is a save test")

        taskDao.save(task)

        taskDao.updateCompleted(task.id)

        assertThat(taskDao.findById(task.id)?.isComplete,`is`(true))
    }

    @Test
    fun writeAndDeleteTaskTest () = runBlocking {
        val task = Task(
            title = "Test 1",
            description = "This is a save test")

        taskDao.save(task)
        assertThat(taskDao.findById(task.id)?.title, `is`(task.title))

        taskDao.deleteById(task.id)
        assertThat(taskDao.findAll().size, `is`(0))
    }

    @Test
    fun deleteAllCompleteTasksTest () = runBlocking {
        val myList = mutableListOf<Task>()
        myList.add(Task(title = "Test 1", description = ""))
        myList.add(Task(title = "Test 2", description = ""))
        myList.add(Task(title = "Test 3", description = ""))

        myList.forEach {
            taskDao.save(it)
        }

        val tasksDb = taskDao.findAll()
        val task1 = tasksDb[0]
        val task2 = tasksDb[1]

        taskDao.updateCompleted(task1.id)
        taskDao.updateCompleted(task2.id)

        taskDao.deleteAllCompleted()
        assertThat(taskDao.findAll().size, `is`(1))
    }

}