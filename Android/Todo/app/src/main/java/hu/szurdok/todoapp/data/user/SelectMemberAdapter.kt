package hu.szurdok.todoapp.data.user

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.group.ChooseGroupAdapter
import hu.szurdok.todoapp.data.group.Group
import kotlinx.android.synthetic.main.member_item.view.*
import kotlinx.android.synthetic.main.member_item.view.ivMemberPic
import kotlinx.android.synthetic.main.member_item.view.tvMemberName
import kotlinx.android.synthetic.main.select_member_item.view.*

class SelectMemberAdapter : RecyclerView.Adapter<SelectMemberAdapter.MemberHolder>() {

    private var members = mutableListOf<User>()
    var itemClickListener : SelectMemberAdapter.MemberItemClickListener? = null
    private var selectedIds = mutableListOf<Int>()

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
            v.setOnClickListener {
                Log.d("click", "clicked! ! !")
            }
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.select_member_item, parent, false)
        return MemberHolder(view)
    }

    override fun onBindViewHolder(holder: MemberHolder, position: Int) {
        holder.user = members[position]
        holder.image.setImageResource(R.drawable.ic_launcher_background)
        holder.name.text = holder.user!!.userName
        if(selectedIds.contains(holder.user!!.id)){
            holder.checkBox.isChecked = true
        }
    }

    override fun getItemCount() = members.size

    private fun clear(){
        val size = itemCount
        members.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun addAll(list : List<User>, alreadySelected : MutableList<Int>){
        selectedIds = alreadySelected

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