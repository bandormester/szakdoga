package hu.szurdok.todoapp.data.models

import androidx.room.Entity

@Entity
data class Check(
    var id : Int,
    var description : String = "",
    var done : Boolean = false
) {
}