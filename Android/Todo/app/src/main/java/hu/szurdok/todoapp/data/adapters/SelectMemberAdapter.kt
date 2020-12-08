package hu.szurdok.todoapp.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.models.User
import hu.szurdok.todoapp.viewmodel.main.ImageLoadingViewModel
import kotlinx.android.synthetic.main.item_select_member.view.*

class SelectMemberAdapter(private val viewModel : ImageLoadingViewModel, val context : Context) : RecyclerView.Adapter<SelectMemberAdapter.MemberHolder>() {

    private var members = mutableListOf<User>()
    var itemClickListener : MemberItemClickListener? = null
    private var selectedUsers = mutableListOf<User>()

    interface MemberItemClickListener {
        fun onMemberSelected(member: User)
        fun onMemberExcluded(member: User)
    }

    inner class MemberHolder(v : View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.ivMemberPic
        val name: TextView = v.tvMemberName
        val checkBox : CheckBox = v.cbMemberSelected
        var user : User? = null

        init {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    user?.let { itemClickListener!!.onMemberSelected(it) }
                }else{
                    user?.let { itemClickListener!!.onMemberExcluded(it) }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_select_member, parent, false)
        return MemberHolder(view)
    }

    override fun onBindViewHolder(holder: MemberHolder, position: Int) {
        holder.user = members[position]
        holder.image.setImageResource(R.drawable.ic_launcher_background)
        holder.name.text = holder.user!!.userName

        if(selectedUsers.contains(holder.user!!)){
            holder.checkBox.isChecked = true
        }

        if(holder.user!!.hasPicture){
            viewModel.getPicture(holder.user!!.id, holder.image, context)
        }
    }

    override fun getItemCount() = members.size

    private fun clear(){
        val size = itemCount
        members.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addAll(list : List<User>, alreadySelected : MutableList<User>){
        selectedUsers = alreadySelected

        for(m in members){
            if(!list.contains(m))
                itemClickListener!!.onMemberExcluded(m)
        }
        clear()
        val size = itemCount
        members.addAll(list)
        notifyItemRangeChanged(size, members.size)
    }
}