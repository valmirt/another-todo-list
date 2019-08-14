package br.dev.valmirt.anothertodolist.ui.home.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.ToDoApplication
import br.dev.valmirt.anothertodolist.base.BaseFragment
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.SELECTED_THEME
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject

class SettingsFragment :
    BaseFragment<SettingsViewModel> (SettingsViewModel::class){

    private val preferences: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.alertMessage.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.hasTasks.observe(this, Observer {
            clear_all.isEnabled = it
        })

        viewModel.deleteResponse.observe(this, Observer {
            clear_all.isEnabled = !it
            if (!it) {
                Toast.makeText(
                    context,
                    getString(R.string.delete_response),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        val preference = preferences.getBoolean(SELECTED_THEME,  false)

        dark_switch.isChecked = preference

        dark_switch.setOnCheckedChangeListener { _, isChecked ->
            val editor = preferences.edit()
            editor.putBoolean(SELECTED_THEME, isChecked)
            editor.apply()

            ToDoApplication.selectedDarkTheme(isChecked)
            activity?.recreate()
        }

        clear_all.setOnClickListener {
            context?.let {
                AlertDialog.Builder(it).run {
                    setTitle(getString(R.string.title_dialog_delete))
                    setMessage(getString(R.string.message_dialog_delete_all))
                    setPositiveButton(getString(R.string.positive_dialog_delete)) { _, _ ->
                        viewModel.clearAllTasks()
                    }
                    setNegativeButton(getString(R.string.negative_dialog_delete)) { _, _ ->}
                    show()
                }
            }
        }

        rate_us.setOnClickListener {
            viewModel.rateThisApp(context)
        }

        share_us.setOnClickListener {
            viewModel.shareWithFriends(context)
        }

//        donate_us.setOnClickListener {
//            viewModel.BuyMeABeer(context)
//        }
    }
}