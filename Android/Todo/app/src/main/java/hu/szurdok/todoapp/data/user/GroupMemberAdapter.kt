package hu.szurdok.todoapp.data.user

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.group.ChooseGroupAdapter
import hu.szurdok.todoapp.data.group.Group
import kotlinx.android.synthetic.main.member_item.view.*

class GroupMemberAdapter : RecyclerView.Adapter<GroupMemberAdapter.MemberHolder>() {

    private var members = mutableListOf<User>()
    var itemClickListener : GroupMemberAdapter.MemberItemClickListener? = null

    interface MemberItemClickListener {
        fun onMemberSelected(member: User)
        fun onMemberRemoved(member: User)
    }

    inner class MemberHolder(v : View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.ivMemberPic
        val name: TextView = v.tvMemberName
        private val removeImage: ImageView = v.ivRemoveMember
        var user : User? = null

        init {
            removeImage.setOnClickListener {
                user?.let { it1 -> itemClickListener?.onMemberRemoved(it1) }
            }
            v.setOnClickListener {
                Log.d("click", "clicked! ! !")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)
        return MemberHolder(view)
    }

    override fun onBindViewHolder(holder: MemberHolder, position: Int) {
        holder.user =members[position]
        holder.image.setImageResource(R.drawable.ic_launcher_background)
        holder.name.text = holder.user!!.userName
    }

    override fun getItemCount() = members.size

    fun addAll(list : List<User>){
        val size = itemCount
        members.addAll(list)
        notifyItemRangeChanged(size, members.size)
    }
}