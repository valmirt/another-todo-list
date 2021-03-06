package br.dev.valmirt.anothertodolist.ui.home.create

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_create.*

class CreateFragment :
    BaseFragment<CreateViewModel>(CreateViewModel::class) {

    private var wait: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservables()
    }

    private fun setupObservables() {
        viewModel.alertMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            wait = false
            edit_description.isEnabled = !wait
            edit_title.isEnabled = !wait
        })

        viewModel.success.observe(viewLifecycleOwner, Observer { success ->
            success?.let { if (it) findNavController().navigate(R.id.create_to_home) }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_button -> {
                if (edit_title.text.toString().isNotEmpty()) {
                    if (!wait) {
                        viewModel.saveNewTask(
                            edit_title.text.toString(),
                            edit_description.text.toString()
                        )
                        wait = true
                        edit_description.isEnabled = !wait
                        edit_title.isEnabled = !wait
                    }
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.empty_title_warning),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}