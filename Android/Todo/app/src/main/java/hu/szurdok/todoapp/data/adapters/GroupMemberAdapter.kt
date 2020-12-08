package hu.szurdok.todoapp.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.models.User
import hu.szurdok.todoapp.viewmodel.main.ImageLoadingViewModel
import kotlinx.android.synthetic.main.item_member.view.*

class GroupMemberAdapter(private val viewModel : ImageLoadingViewModel, val context : Context) : RecyclerView.Adapter<GroupMemberAdapter.MemberHolder>() {

    private var members = mutableListOf<User>()
    var itemClickListener : MemberItemClickListener? = null
    var removeClickListener : MemberItemRemoveClickListener? = null
    var removeAble : Boolean = true

    interface MemberItemClickListener {
        fun onMemberSelected(member: User)
    }

    interface MemberItemRemoveClickListener{
        fun onMemberRemoved(member: User)
    }

    inner class MemberHolder(v : View) : RecyclerView.ViewHolder(v) {
        val image: ImageView = v.ivMemberPic
        val name: TextView = v.tvMemberName
        private val removeImage: ImageView = v.ivRemoveMember
        var user : User? = null

        init {
            if(!removeAble) removeImage.visibility = View.GONE
            removeImage.setOnClickListener {
                user?.let { it1 -> removeClickListener?.onMemberRemoved(it1) }
            }
            v.setOnClickListener {
                user?.let { it1 -> itemClickListener?.onMemberSelected(it1) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return MemberHolder(view)
    }

    override fun onBindViewHolder(holder: MemberHolder, position: Int) {
        holder.user =members[position]
        holder.image.setImageResource(R.drawable.ic_launcher_background)
        holder.name.text = holder.user!!.userName

        if(holder.user!!.hasPicture){
            viewModel.getPicture(holder.user!!.id, holder.image, context)
        }
    }

    override fun getItemCount() = members.size

    fun addAll(list : List<User>){
        val size = itemCount
        members.addAll(list)
        notifyItemRangeChanged(size, members.size)
    }
}