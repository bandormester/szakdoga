package hu.szurdok.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey
    val id : Int,
    val fullName : String,
    val userName : String,
    val email : String,
    val password : String,
    val hasPicture : Boolean
) {
    override fun toString(): String {
        return "$id - $fullName - $userName - $password - $email - $hasPicture"
    }
}