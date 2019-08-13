package br.dev.valmirt.anothertodolist.ui.home.settings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.base.BaseViewModel
import br.dev.valmirt.anothertodolist.repository.TaskRepository
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.APP_NAME
import kotlinx.coroutines.launch
import org.koin.core.inject

class SettingsViewModel: BaseViewModel() {
    private val _hasTasks: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also {
            viewModelScope.launch {
                it.value = taskRepository.getAllTasks().value.isNotEmpty()
            }
        }
    }
    private val _alertMessage = MutableLiveData<String>()
    private val _deleteResponse = MutableLiveData<Boolean>()

    val alertMessage: LiveData<String>
        get() = _alertMessage

    val deleteResponse: LiveData<Boolean>
        get() = _deleteResponse

    val hasTasks: LiveData<Boolean>
        get() = _hasTasks

    private val taskRepository: TaskRepository by inject()

    fun clearAllTasks() {
        viewModelScope.launch {
            val response = taskRepository.deleteAllTasks()

            _deleteResponse.value = response.value

            if (response.message.isNotEmpty())
                _alertMessage.value = response.message
        }
    }

    fun rateThisApp(context: Context?) {
        context?.startActivity(Intent(Intent.ACTION_VIEW,
            Uri.parse("market://details?id=$APP_NAME")))
    }

    fun shareWithFriends(context: Context?) {
        val message = context?.getString(R.string.share_message) +
                " " +
                "http://play.google.com/store/apps/details?id=$APP_NAME"

        val intent = Intent()
        intent.type = "text/plain"
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, message)

        context?.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
    }

//    fun BuyMeABeer (context: Context?) {
//
//    }
}