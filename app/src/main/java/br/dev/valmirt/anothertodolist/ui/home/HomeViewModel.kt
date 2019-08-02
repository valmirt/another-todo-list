package br.dev.valmirt.anothertodolist.ui.home

import androidx.lifecycle.ViewModel
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeViewModel : ViewModel(), KoinComponent {

    private val taskRepository: TaskRepository by inject()

//    val getAllTasks()
}