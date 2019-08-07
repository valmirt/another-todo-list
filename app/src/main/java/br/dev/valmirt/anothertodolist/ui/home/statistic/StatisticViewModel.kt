package br.dev.valmirt.anothertodolist.ui.home.statistic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class StatisticViewModel: ViewModel(), KoinComponent {

    private val taskRepository: TaskRepository by inject()

    val emptyTasks: MutableLiveData<Boolean> = MutableLiveData()

    val activeResult: MutableLiveData<Float> = MutableLiveData()

    val totalResult: MutableLiveData<String> = MutableLiveData()

    val completedResult: MutableLiveData<Float> = MutableLiveData()

    init {
        viewModelScope.launch {
            val response = taskRepository.getAllTasks().value
            val total = response.size
            val active = response.count { !it.isComplete }


            if (total == 0) emptyTasks.value = true
            else {
                totalResult.value = total.toString()
                activeResult.value = 100f * active / total
                completedResult.value = 100f * (total - active) / total
            }
        }
    }
}