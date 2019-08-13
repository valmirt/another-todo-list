package br.dev.valmirt.anothertodolist.ui.home.statistic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.dev.valmirt.anothertodolist.base.BaseViewModel
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import kotlinx.coroutines.launch
import org.koin.core.inject

class StatisticViewModel: BaseViewModel() {
    private val _emptyTasks = MutableLiveData<Boolean>()
    private val _activeResult = MutableLiveData<Float>()
    private val _totalResult = MutableLiveData<String>()
    private val _completedResult = MutableLiveData<Float>()

    private val taskRepository: TaskRepository by inject()

    val emptyTasks: LiveData<Boolean>
        get() = _emptyTasks

    val activeResult: LiveData<Float>
        get() = _activeResult

    val totalResult: LiveData<String>
        get() = _totalResult

    val completedResult: LiveData<Float>
        get() = _completedResult

    init {
        viewModelScope.launch {
            val response = taskRepository.getAllTasks().value
            val total = response.size
            val active = response.count { !it.isComplete }


            if (total == 0) _emptyTasks.value = true
            else {
                _totalResult.value = total.toString()
                _activeResult.value = 100f * active / total
                _completedResult.value = 100f * (total - active) / total
            }
        }
    }
}