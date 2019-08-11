package br.dev.valmirt.anothertodolist.ui.home.settings

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.ToDoApplication
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.DARK_THEME
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.SELECTED_THEME
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    private val preferences: SharedPreferences? by lazy {
        context?.getSharedPreferences(DARK_THEME, Application.MODE_PRIVATE)
    }

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

        val preference = preferences?.getBoolean(SELECTED_THEME,  false)

        dark_switch.isChecked = preference ?: false

        dark_switch.setOnCheckedChangeListener { _, isChecked ->
            val editor = preferences?.edit()
            editor?.putBoolean(SELECTED_THEME, isChecked)
            editor?.apply()

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