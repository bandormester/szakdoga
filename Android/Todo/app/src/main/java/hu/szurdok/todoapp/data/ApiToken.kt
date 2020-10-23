package hu.szurdok.todoapp.data

import java.io.Serializable

data class ApiToken(
    val id : Int,
    val token : String
) : Serializable {
}