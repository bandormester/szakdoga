package hu.szurdok.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.szurdok.todoapp.data.models.misc.Importance
import java.io.Serializable

@Entity
data class TaskCard(
    @PrimaryKey
    val id : Int,
    var ownerId : Int,
    var groupId : Int,
    var label : String,
    var importance : Importance,
    var hasDescription : Boolean,
    var hasPlace : Boolean,
    var hasAssignees : Boolean,
    var hasChecklist : Boolean)
    :Serializable{
}