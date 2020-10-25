package hu.szurdok.todoapp.data.task

import hu.szurdok.todoapp.data.Importance
import hu.szurdok.todoapp.data.check.Check

data class Task(
    val id : Int,
    var label : String,
    var importance : Importance,
    var description : String?,
    var lat : Double?,
    var lon : Double?,
    var deadline : Double,
    var assignees : MutableList<Int>?,
    var checklist : MutableList<Check>?
    ) {
}