package hu.szurdok.todoapp.data.group

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group (
    @PrimaryKey
    val id : Int,
    val name : String,
    val joinCode : String,
    val picture : String,
    val ownerId : String
) {
    override fun toString(): String {
        return "$id - $joinCode - $picture - $ownerId"
    }
}