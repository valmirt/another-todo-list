package br.dev.valmirt.anothertodolist.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.valmirt.anothertodolist.model.Filter
import br.dev.valmirt.anothertodolist.model.Filter.*
import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val taskRepository: TaskRepository by inject()

    val alertMessage: MutableLiveData<String> = MutableLiveData()

    val tasks: MutableLiveData<List<Task>> = MutableLiveData()

    val spinner: MutableLiveData<Boolean> = MutableLiveData()

    fun updateTaskList(filter: Filter = ALL) {
        launchDataLoad {
            val response = taskRepository.getAllTasks()

            tasks.value = when(filter) {
                ALL -> response.value
                ACTIVE -> response.value.filter { !it.isComplete }
                COMPLETED -> response.value.filter { it.isComplete }
            }

            if(response.message.isNotEmpty())
                alertMessage.value = response.message
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            spinner.value = true
            block()
            spinner.value = false
        }
    }
}