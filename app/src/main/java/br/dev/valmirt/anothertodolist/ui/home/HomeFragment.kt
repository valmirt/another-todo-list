package br.dev.valmirt.anothertodolist.ui.home

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.model.Filter.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private var filter = ALL

    private val adapterTask = HomeAdapter()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapterTask.setOnItemClickListener(object : HomeAdapter.OnItemClickListener{
            override fun onClick(position: Int, view: View?) {
                //Detail task
            }
        })
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

        task_list.layoutManager = LinearLayoutManager(context)
        task_list.setHasFixedSize(true)
        task_list.adapter = adapterTask


        context?.let {
            refresh_home.setColorSchemeColors(
                ContextCompat.getColor(it, R.color.colorAccent),
                ContextCompat.getColor(it, R.color.colorSpinner),
                ContextCompat.getColor(it, R.color.colorPrimary))

            refresh_home.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(it, R.color.colorBackgroundSpinner))
        }


        refresh_home.setOnRefreshListener {
            viewModel.updateTaskList()
        }

        viewModel.tasks.observe(this, Observer {
            adapterTask.replaceData(it)
            if (it.isNotEmpty()) {
                image_free.visibility = View.GONE
                text_free.visibility = View.GONE
            } else {
                image_free.visibility = View.VISIBLE
                text_free.visibility = View.VISIBLE
            }
        })

        viewModel.spinner.observe(this, Observer {
            refresh_home.isRefreshing = it
        })

        viewModel.alertMessage.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
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
                filter = when(it.itemId) {
                    R.id.active -> ACTIVE
                    R.id.completed -> COMPLETED
                    else -> ALL
                }
                //Call function to reload tasks
                true
            }

            show()
        }
    }
}