package br.dev.valmirt.anothertodolist.ui.home.detail

import androidx.lifecycle.ViewModel
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailViewModel : ViewModel(), KoinComponent {

    private val taskRepository: TaskRepository by inject()


}