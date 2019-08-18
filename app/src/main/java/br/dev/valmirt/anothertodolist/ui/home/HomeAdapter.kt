package br.dev.valmirt.anothertodolist.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.dev.valmirt.anothertodolist.R
import br.dev.valmirt.anothertodolist.model.Task

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val tasks = mutableListOf<Task>()
    private lateinit var mClickListener: OnItemClickListener

    fun setOnItemClickListener(cl: OnItemClickListener) {
        mClickListener = cl
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val noteView = inflater.inflate(R.layout.adapter_home, parent, false)
        return HomeViewHolder(noteView)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val task = tasks[position]
        holder.fillData(task)

        holder.itemView.setOnClickListener {
            mClickListener.onClick(task.id, holder.itemView)
        }

        holder.checkBox.setOnClickListener {
            holder.completeTask(holder.checkBox.isChecked)
            mClickListener.onChecked(task.id, it)
        }
    }

    fun replaceData (data: List<Task>) {
        tasks.clear()
        tasks.addAll(data)
        notifyDataSetChanged()
    }

    class HomeViewHolder (noteView: View)
        : RecyclerView.ViewHolder(noteView) {

        val checkBox: CheckBox = noteView.findViewById(R.id.checkBox_task)
        private val task = noteView.findViewById<TextView>(R.id.task_title)
        private val date = noteView.findViewById<TextView>(R.id.task_date)

        fun fillData (data: Task) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                task.setTextAppearance(R.style.TextPrimaryStyle)
                date.setTextAppearance(R.style.TextSecondaryStyle)
            }
            checkBox.isChecked = data.isComplete
            task.text = data.title
            date.text = data.date

            completeTask(checkBox.isChecked)
        }

        fun completeTask (isCompleted: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (isCompleted) {
                    task.setTextAppearance(R.style.TextPrimaryStyleChecked)
                    date.setTextAppearance(R.style.TextSecondaryStyleChecked)
                } else {
                    task.setTextAppearance(R.style.TextPrimaryStyle)
                    date.setTextAppearance(R.style.TextSecondaryStyle)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(idTask: String, view: View?)

        fun onChecked(idTask: String, view: View?)
    }
}