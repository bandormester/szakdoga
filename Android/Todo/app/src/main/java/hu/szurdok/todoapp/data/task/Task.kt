package hu.szurdok.todoapp.data.task

import hu.szurdok.todoapp.data.Importance

data class Task(
    val id : Int,
    var label : String,
    var importance : Importance,
    var description : String,
    var lat : Double,
    var lon : Double,
    var deadline : Double,
    var assignees : List<Int>,
    var checklist : List<String>
    ) {
}