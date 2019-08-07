package br.dev.valmirt.anothertodolist.ui.home.statistic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.dev.valmirt.anothertodolist.R
import kotlinx.android.synthetic.main.fragment_statistic.*

class StatisticFragment: Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(StatisticViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.emptyTasks.observe(this, Observer {
            if (it) default_text.visibility = View.VISIBLE
        })

        viewModel.activeResult.observe(this, Observer {
            active_tasks.text = getString(R.string.active_statistic, it)
        })

        viewModel.completedResult.observe(this, Observer {
            completed_tasks.text = getString(R.string.completed_statistic, it)
        })

        viewModel.totalResult.observe(this, Observer {
            total_tasks.text = getString(R.string.total_statistic, it)
        })
    }
}