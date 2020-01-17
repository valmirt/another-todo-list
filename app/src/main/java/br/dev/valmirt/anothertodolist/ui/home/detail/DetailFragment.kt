package br.dev.valmirt.anothertodolist.ui.home.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.base.BaseFragment
import br.dev.valmirt.anothertodolist.model.Task
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.SELECTED_TASK
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment :
    BaseFragment<DetailViewModel>(DetailViewModel::class) {

    private lateinit var idTask: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idTask = arguments?.getString(SELECTED_TASK) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTaskDetail(idTask)
        setupObservables()
    }

    private fun setupObservables() {
        viewModel.alertMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.task.observe(viewLifecycleOwner, Observer { task ->
            task?.let { fetchData(it) }
        })

        viewModel.spinner.observe(viewLifecycleOwner, Observer { isActive ->
            isActive?.let { setupLoading(it) }
        })

        viewModel.deleted.observe(viewLifecycleOwner, Observer { isDeleted ->
            isDeleted?.let { if (it) findNavController().navigate(R.id.detail_to_home) }
        })
    }

    private fun fetchData(task: Task) {
        detail_title.text = task.title
        detail_date.text = task.date
        if (task.description.isNotEmpty()) {
            detail_description.text = task.description
        }
    }


    private fun setupLoading(isActive: Boolean) {
        if (isActive) {
            loading_detail.visibility = View.VISIBLE
            detail_layout.visibility = View.GONE
        } else {
            loading_detail.visibility = View.GONE
            detail_layout.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_button -> {
                context?.let {
                    AlertDialog.Builder(it).run {
                        setTitle(getString(R.string.title_dialog_delete))
                        setMessage(getString(R.string.message_dialog_delete))
                        setPositiveButton(getString(R.string.positive_dialog_delete)) { _, _ ->
                            viewModel.deleteThisTask(idTask)
                        }
                        setNegativeButton(getString(R.string.negative_dialog_delete)) { _, _ -> }
                        show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}