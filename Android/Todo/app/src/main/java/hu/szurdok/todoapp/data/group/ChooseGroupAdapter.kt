package hu.szurdok.todoapp.data.group

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.szurdok.todoapp.R
import kotlinx.android.synthetic.main.group_item.view.*

class ChooseGroupAdapter() : RecyclerView.Adapter<ChooseGroupAdapter.GroupViewHolder>() {

    private var groups = mutableListOf<Group>()
    var itemClickListener : GroupItemClickListener? = null

    inner class GroupViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val image = view.ivGroupPic
        val name = view.tvGroupName

        var group : Group? = null

        init{
            view.setOnClickListener{
                group?.let { it1 -> itemClickListener?.onGroupSelected(it1) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.group =groups[position]
        holder.image.setImageResource(R.drawable.ic_launcher_background)
        holder.name.text = holder.group!!.name
    }

    override fun getItemCount() = groups.size

    fun clear(){
        val size = itemCount
        groups.clear()
        notifyItemRangeChanged(size, 0)
    }

    fun addAll(list : List<Group>){
        val size = itemCount
        groups.addAll(list)
        notifyItemRangeChanged(size, groups.size)
    }

    interface GroupItemClickListener {
        fun onGroupSelected(group: Group)
    }
}