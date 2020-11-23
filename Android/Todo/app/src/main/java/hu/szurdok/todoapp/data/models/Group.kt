package hu.szurdok.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Group (
    @PrimaryKey
    val id : Int,
    val name : String,
    val joinCode : String,
    val description : String,
    val hasPicture : Boolean,
    val ownerId : String
) {
    override fun toString(): String {
        return "$id - $joinCode - $hasPicture - $ownerId"
    }
}