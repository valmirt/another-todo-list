package br.dev.valmirt.anothertodolist.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.dev.valmirt.anothertodolist.base.BaseViewModel
import br.dev.valmirt.anothertodolist.model.Filter
import br.dev.valmirt.anothertodolist.model.Filter.*
import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.DATE_FORMAT
import org.koin.core.inject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : BaseViewModel() {
    private val _alertMessage = MutableLiveData<String>()
    private val _spinner = MutableLiveData<Boolean>()
    private val _tasks = MutableLiveData<List<Task>>()

    private val taskRepository: TaskRepository by inject()

    val alertMessage: LiveData<String>
        get() = _alertMessage

    val tasks: LiveData<List<Task>>
        get() = _tasks

    val spinner: LiveData<Boolean>
        get() = _spinner

    fun updateTaskList(filter: Filter = ALL) {
        launchDataLoad(_spinner) {
            val response = taskRepository.getAllTasks()

            _tasks.value = when(filter) {
                ALL -> response.value.sortedByDescending { getDate(it.date) }
                ACTIVE -> response.value
                    .filter { !it.isComplete }
                    .sortedByDescending { getDate(it.date) }
                COMPLETED -> response.value
                    .filter { it.isComplete }
                    .sortedByDescending { getDate(it.date) }
            }

            setErrorMessage(response.message)
        }
    }

    private fun getDate(date: String) : Date {
        return try {
            SimpleDateFormat(DATE_FORMAT, Locale.US).parse(date)
        } catch (e: ParseException) {
            _alertMessage.value = e.message
            Date()
        }
    }

    fun completedTask (id: String) {
        launchDataLoad(_spinner) {
            val response = taskRepository.completeTask(id)

            setErrorMessage(response.message)
        }
    }

    private fun setErrorMessage (message: String) {
        if (message.isNotEmpty()) {
            _alertMessage.value = message
        }
    }
}