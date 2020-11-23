package hu.szurdok.todoapp.data.models.misc

import java.io.Serializable

data class ApiToken(
    val id : Int,
    val token : String
) : Serializable {
}