package br.dev.valmirt.anothertodolist.ui.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.dev.valmirt.anothertodolist.base.BaseViewModel
import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import org.koin.core.inject

class DetailViewModel : BaseViewModel() {
    private val _alertMessage = MutableLiveData<String>()
    private val _spinner = MutableLiveData<Boolean>()
    private val _task = MutableLiveData<Task>()
    private val _deleted = MutableLiveData<Boolean>()

    private val taskRepository: TaskRepository by inject()

    val spinner: LiveData<Boolean>
        get() = _spinner

    val alertMessage: LiveData<String>
        get() = _alertMessage

    val task: LiveData<Task>
        get() = _task

    val deleted: LiveData<Boolean>
        get() = _deleted

    fun getTaskDetail(id: String) {
        launchDataLoad (_spinner) {
            val response = taskRepository.getTask(id)

            response.value?.let {
                _task.value = it
            }

            setErrorMessage(response.message)
        }
    }

    fun deleteThisTask(id: String) {
        launchDataLoad(_spinner) {
            val response = taskRepository.deleteTask(id)
            response.value?.let {
                _deleted.value = true
            }

            setErrorMessage(response.message)
        }
    }

    private fun setErrorMessage (message: String) {
        if (message.isNotEmpty()) {
            _alertMessage.value = message
        }
    }
}