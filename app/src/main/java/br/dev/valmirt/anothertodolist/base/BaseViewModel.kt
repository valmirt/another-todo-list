package br.dev.valmirt.anothertodolist.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

open class BaseViewModel: ViewModel(), KoinComponent {

    protected fun launchDataLoad(spinner: MutableLiveData<Boolean>,
                                 block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            spinner.value = true
            block()
            spinner.value = false
        }
    }
}