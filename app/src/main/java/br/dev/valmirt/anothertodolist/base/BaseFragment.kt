package br.dev.valmirt.anothertodolist.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

open class BaseFragment<VM : ViewModel>
    (private val viewModelClass: KClass<VM>) : Fragment() {

    val viewModel: VM by lazy {
        ViewModelProvider(this).get(viewModelClass.java)
    }

}