package br.dev.valmirt.anothertodolist.ui.home

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.model.Filter.*

class HomeFragment : Fragment() {

    private var filter = ALL

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
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

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_task -> {
//                findNavController().navigate(R.id.home_to_settings)
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