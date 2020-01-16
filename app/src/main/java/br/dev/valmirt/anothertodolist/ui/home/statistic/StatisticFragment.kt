package br.dev.valmirt.anothertodolist.ui.home.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_statistic.*

class StatisticFragment :
    BaseFragment<StatisticViewModel>(StatisticViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.emptyTasks.observe(viewLifecycleOwner, Observer {
            if (it) default_text.visibility = View.VISIBLE
        })

        viewModel.activeResult.observe(viewLifecycleOwner, Observer {
            active_tasks.text = getString(R.string.active_statistic, it)
        })

        viewModel.completedResult.observe(viewLifecycleOwner, Observer {
            completed_tasks.text = getString(R.string.completed_statistic, it)
        })

        viewModel.totalResult.observe(viewLifecycleOwner, Observer {
            total_tasks.text = getString(R.string.total_statistic, it)
        })
    }
}