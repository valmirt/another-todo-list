package br.dev.valmirt.anothertodolist.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.base.BaseFragment
import br.dev.valmirt.anothertodolist.model.Filter
import br.dev.valmirt.anothertodolist.model.Filter.*
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.LAST_LIST
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.SELECTED_TASK
import br.dev.valmirt.anothertodolist.utils.toModelFilter
import br.dev.valmirt.anothertodolist.utils.toTranslatedString
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment :
    BaseFragment<HomeViewModel>(HomeViewModel::class) {

    private lateinit var filter: Filter

    private val adapterTask: HomeAdapter by lazy {
        HomeAdapter()
    }

    private val preferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filter = preferences.getString(LAST_LIST, ALL.toString())
            ?.toModelFilter() ?: ALL

        viewModel.updateTaskList(filter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTaskList()

        setUpSwipeRefresh()

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            adapterTask.replaceData(it)
            if (it.isNotEmpty()) {
                image_free.visibility = View.GONE
                text_free.visibility = View.GONE
                adapterTask.replaceData(it)
            } else {
                image_free.visibility = View.VISIBLE
                text_free.visibility = View.VISIBLE
            }
        })

        viewModel.spinner.observe(viewLifecycleOwner, Observer {
            refresh_home.isRefreshing = it
        })

        viewModel.alertMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpTaskList() {
        adapterTask.itemClicked = {
            val bundle = Bundle()
            bundle.putString(SELECTED_TASK, it)
            findNavController().navigate(R.id.home_to_detail, bundle)
        }

        adapterTask.itemChecked = { viewModel.completedTask(it) }
        task_list.layoutManager = LinearLayoutManager(context)
        task_list.adapter = adapterTask

        title_list.text = filter.toTranslatedString(context)
    }

    private fun setUpSwipeRefresh() {
        context?.let {
            refresh_home.setColorSchemeColors(
                ContextCompat.getColor(it, R.color.colorAccent),
                ContextCompat.getColor(it, R.color.colorSpinner),
                ContextCompat.getColor(it, R.color.colorPrimary)
            )

            refresh_home.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(it, R.color.colorBackgroundSpinner)
            )
        }


        refresh_home.setOnRefreshListener {
            viewModel.updateTaskList(filter)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_task -> {
                findNavController().navigate(R.id.home_to_create)
                true
            }
            R.id.filter_task -> {
                showPopUpMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.filter_task) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_tasks, menu)

            setOnMenuItemClickListener {
                filter = when (it.itemId) {
                    R.id.active -> ACTIVE
                    R.id.completed -> COMPLETED
                    else -> ALL
                }
                title_list.text = filter.toTranslatedString(context)

                val editor = preferences.edit()
                editor?.putString(LAST_LIST, filter.toString())
                editor?.apply()

                viewModel.updateTaskList(filter)
                true
            }

            show()
        }
    }
}