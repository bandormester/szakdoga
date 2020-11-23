package hu.szurdok.todoapp.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.models.Group
import hu.szurdok.todoapp.viewmodel.main.group.ChooseGroupViewModel
import kotlinx.android.synthetic.main.group_item.view.*

class ChooseGroupAdapter(val context : Context,val chooseGroupViewModel: ChooseGroupViewModel) : RecyclerView.Adapter<ChooseGroupAdapter.GroupViewHolder>() {

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
        holder.group = groups[position]
        holder.image.setImageResource(R.drawable.ic_launcher_background)
        holder.name.text = holder.group!!.name


        if(holder.group!!.hasPicture){
          // chooseGroupViewModel.getPicture(holder.group!!.id)
            val glideUrl = GlideUrl("http://10.0.2.2:8080/group/"+holder.group!!.id+"/pic")
            val option = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
            Glide.with(context)
                .load(glideUrl)
                .centerCrop()
                .apply(option)
                .into(holder.image)
        }
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