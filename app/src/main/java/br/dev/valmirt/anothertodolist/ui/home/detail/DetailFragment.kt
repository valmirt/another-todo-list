package br.dev.valmirt.anothertodolist.ui.home.detail

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.dev.valmirt.anothertodolist.R

class DetailFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_detail, container, false)
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
                            viewModel.deleteThisTask()
                        }
                        setNegativeButton(getString(R.string.negative_dialog_delete)) { _, _ ->}
                        show()
                    }
                }
                true
            }
            R.id.edit_button -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}