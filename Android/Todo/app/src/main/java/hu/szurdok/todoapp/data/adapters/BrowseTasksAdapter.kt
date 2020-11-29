package hu.szurdok.todoapp.data.adapters

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.models.misc.Importance
import hu.szurdok.todoapp.data.models.TaskCard
import kotlinx.android.synthetic.main.pictogram.view.*
import kotlinx.android.synthetic.main.task_item_card.view.*

class BrowseTasksAdapter(val activity: Activity) : RecyclerView.Adapter<BrowseTasksAdapter.TaskViewHolder>() {

    private var tasks = mutableListOf<TaskCard>()
    var itemClickListener : TaskItemClickListener? = null

    inner class TaskViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val label = view.tvTaskLabel
        val layout = view.llTaskItem
        val pictoHolder = view.llPictogramHolder

        var taskCard : TaskCard? = null

        init{
            view.setOnClickListener{
                taskCard?.let { it1 -> itemClickListener?.onTaskSelected(it1) }
            }
        }

        fun inflateDetail(drawableId : Int){
            val pictogram = activity.layoutInflater.inflate(R.layout.pictogram, pictoHolder, false)
            pictogram.ivPictogram.setImageDrawable(ResourcesCompat.getDrawable(activity.resources, drawableId, null))
            pictoHolder.addView(pictogram)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item_card, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = tasks.size

    fun clear(){
        val size = itemCount
        tasks.clear()
        notifyItemRangeChanged(size, 0)
    }

    fun addAll(list : List<TaskCard>){
        val size = itemCount
        tasks.addAll(list)
        notifyItemRangeChanged(size, tasks.size)
    }

    fun getSpan(position: Int) : Int{
        if(tasks[position].importance == Importance.CRUCIAL) return 2
        return 1
    }

    interface TaskItemClickListener {
        fun onTaskSelected(task: TaskCard)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskCard = tasks[position]
        holder.label.text = holder.taskCard?.label

        if(holder.taskCard!!.hasAssignees){
            holder.inflateDetail(R.drawable.ic_baseline_has_person)
        }
        if(holder.taskCard!!.hasChecklist){
            holder.inflateDetail(R.drawable.ic_baseline_has_checklist)
        }
        if(holder.taskCard!!.hasDescription){
            holder.inflateDetail(R.drawable.ic_baseline_has_description)
        }
        if(holder.taskCard!!.hasPlace){
            holder.inflateDetail(R.drawable.ic_baseline_has_place)
        }

        val bgColor = when(holder.taskCard!!.importance){
            Importance.IMPORTANT -> R.color.important
            Importance.CRUCIAL -> R.color.crucial
            else -> R.color.regular
        }
        holder.layout.setBackgroundColor(ResourcesCompat.getColor(activity.resources, bgColor, null))
    }

}