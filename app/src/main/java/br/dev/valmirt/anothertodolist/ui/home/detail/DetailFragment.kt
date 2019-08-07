package br.dev.valmirt.anothertodolist.ui.home.detail

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.utils.Constants.Companion.SELECTED_TASK
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

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

        viewModel.alertMessage.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        viewModel.task.observe(this, Observer {
            detail_title.text = it.title
            detail_date.text = it.date
            if (it.description.isNotEmpty())
                detail_description.text = it.description
        })

        viewModel.spinner.observe(this, Observer {
            if (it) {
                loading_detail.visibility = View.VISIBLE
                detail_layout.visibility = View.GONE
            } else {
                loading_detail.visibility = View.GONE
                detail_layout.visibility = View.VISIBLE
            }
        })

        viewModel.deleted.observe(this, Observer {
            if (it) findNavController().navigate(R.id.detail_to_home)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.delete_button -> {
                context?.let {
                    AlertDialog.Builder(it).run {
                        setTitle(getString(R.string.title_dialog_delete))
                        setMessage(getString(R.string.message_dialog_delete))
                        setPositiveButton(getString(R.string.positive_dialog_delete)) { _, _ ->
                            viewModel.deleteThisTask(idTask)
                        }
                        setNegativeButton(getString(R.string.negative_dialog_delete)) { _, _ ->}
                        show()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}