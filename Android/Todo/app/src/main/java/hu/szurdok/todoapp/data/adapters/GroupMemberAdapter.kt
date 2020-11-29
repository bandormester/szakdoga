package hu.szurdok.todoapp.data.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.models.User
import kotlinx.android.synthetic.main.member_item.view.*

class GroupMemberAdapter(val context : Context) : RecyclerView.Adapter<GroupMemberAdapter.MemberHolder>() {

    private var members = mutableListOf<User>()
    var itemClickListener : MemberItemClickListener? = null
    var removeAble : Boolean = true

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
            if(!removeAble) removeImage.visibility = View.GONE
            removeImage.setOnClickListener {
                user?.let { it1 -> itemClickListener?.onMemberRemoved(it1) }
            }
            v.setOnClickListener {
                user?.let { it1 -> itemClickListener?.onMemberSelected(it1) }
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

        Log.d("haspic",holder.user!!.hasPicture.toString())

        if(holder.user!!.hasPicture){
            val glideUrl = GlideUrl("http://86.59.209.1:8080/user/"+holder.user!!.id+"/pic")
            val option = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
            Glide.with(context)
                .load(glideUrl)
                .apply(option)
                .into(holder.image)
        }
    }

    override fun getItemCount() = members.size

    fun addAll(list : List<User>){
        val size = itemCount
        members.addAll(list)
        notifyItemRangeChanged(size, members.size)
    }
}