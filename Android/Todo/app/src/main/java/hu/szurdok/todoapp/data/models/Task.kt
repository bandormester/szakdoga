package hu.szurdok.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.szurdok.todoapp.data.models.misc.Importance

@Entity
data class Task(
    @PrimaryKey
    val id : Int,
    var ownerId : Int,
    var groupId : Int,
    var label : String,
    var importance : Importance,
    var description : String?,
    var lat : Double?,
    var lon : Double?,
    var assignees : MutableList<User>?,
    var checklist : MutableList<Check>?
    ) {
}