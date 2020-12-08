package hu.szurdok.todoapp.data.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.models.Check
import kotlinx.android.synthetic.main.item_checklist.view.*

class ChecklistAdapter(private val editable : Boolean) : RecyclerView.Adapter<ChecklistAdapter.CheckHolder>() {

    private var checks = mutableListOf<Check>()
    var itemClickListener : CheckItemClickListener? = null

    interface CheckItemClickListener {
        fun onCheckChecked(check: Check)
    }

    inner class CheckHolder(v : View) : RecyclerView.ViewHolder(v) {
        val description: EditText = v.etCheckDescription
        val checkBox : CheckBox = v.cbCheckDone
        var check : Check? = null

        init {
            if(!editable) description.isEnabled = false

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    check?.let { itemClickListener?.onCheckChecked(it) }
                }
            }

            description.doOnTextChanged { text, _, _, _ ->
                if(editable){
                    check?.description = text.toString()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_checklist, parent, false)
        return CheckHolder(view)
    }

    override fun onBindViewHolder(holder: CheckHolder, position: Int) {
        holder.check = checks[position]
        holder.checkBox.isChecked = holder.check!!.done
        holder.description.setText(holder.check!!.description)
    }

    override fun getItemCount() = checks.size

    private fun clear(){
        val size = itemCount
        checks.clear()
        notifyItemRangeRemoved(0, size)
    }

    fun add(check : Check){
        val size = itemCount
        checks.add(check)
        notifyItemRangeChanged(size, itemCount)
    }

    fun addAll(newChecks : List<Check>){
        clear()
        val size = itemCount
        checks.addAll(newChecks)
        notifyItemRangeChanged(size, itemCount)
    }

    fun remove(check : Check){
        val index = checks.indexOf(check)
        checks.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, itemCount)
    }
}