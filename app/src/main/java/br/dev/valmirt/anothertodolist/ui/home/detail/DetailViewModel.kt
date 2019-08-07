package br.dev.valmirt.anothertodolist.ui.home.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailViewModel : ViewModel(), KoinComponent {

    private val taskRepository: TaskRepository by inject()

    val spinner: MutableLiveData<Boolean> = MutableLiveData()
    val alertMessage: MutableLiveData<String> = MutableLiveData()
    val task: MutableLiveData<Task> = MutableLiveData()
    val deleted: MutableLiveData<Boolean> = MutableLiveData()

    fun getTaskDetail(id: String) {
        launchDataLoad {
            val response = taskRepository.getTask(id)

            response.value?.let {
                task.value = it
            }

            setErrorMessage(response.message)
        }
    }

    fun deleteThisTask(id: String) {
        launchDataLoad {
            val response = taskRepository.deleteTask(id)
            response.value?.let {
                deleted.value = true
            }

            setErrorMessage(response.message)
        }
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            spinner.value = true
            block()
            spinner.value = false
        }
    }

    private fun setErrorMessage (message: String) {
        if (message.isNotEmpty()) {
            alertMessage.value = message
        }
    }
}