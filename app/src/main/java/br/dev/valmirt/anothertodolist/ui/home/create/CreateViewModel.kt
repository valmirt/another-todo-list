package br.dev.valmirt.anothertodolist.ui.home.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class CreateViewModel : ViewModel(), KoinComponent {

    private val taskRepository: TaskRepository by inject()

    val alertMessage: MutableLiveData<String> = MutableLiveData()

    val success: MutableLiveData<Boolean> = MutableLiveData()

    fun saveNewTask(title: String, desc: String) {
        viewModelScope.launch {
            val response = taskRepository.saveTask(
                Task(title = title, description = desc))

            response.value?.let {
                success.value = true
            }

            if (response.message.isNotEmpty()) {
                alertMessage.value = response.message
                success.value = false
            }
        }
    }
}