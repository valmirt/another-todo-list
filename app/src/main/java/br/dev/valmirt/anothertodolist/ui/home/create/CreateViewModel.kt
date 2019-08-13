package br.dev.valmirt.anothertodolist.ui.home.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.dev.valmirt.anothertodolist.base.BaseViewModel
import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import kotlinx.coroutines.launch
import org.koin.core.inject

class CreateViewModel : BaseViewModel() {
    private val _success = MutableLiveData<Boolean>()
    private val _alertMessage = MutableLiveData<String>()

    val alertMessage: LiveData<String>
    get() = _alertMessage

    val success: LiveData<Boolean>
        get() = _success

    private val taskRepository: TaskRepository by inject()

    fun saveNewTask(title: String, desc: String) {
        viewModelScope.launch {
            val response = taskRepository.saveTask(
                Task(title = title, description = desc))

            response.value?.let {
                _success.value = true
            }

            if (response.message.isNotEmpty()) {
                _alertMessage.value = response.message
                _success.value = false
            }
        }
    }
}