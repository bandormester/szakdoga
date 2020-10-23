package hu.szurdok.todoapp.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.Importance
import hu.szurdok.todoapp.data.RegistrationStatus
import hu.szurdok.todoapp.data.repository.main.CreateTaskRepository
import hu.szurdok.todoapp.data.task.Task

class CreateTaskViewModel(
    private val createTaskRepository: CreateTaskRepository,
    private val token : ApiToken,
    private val groupId : Int
) : ViewModel() {
    val creationStatus : LiveData<RegistrationStatus> = createTaskRepository.subscribeStatus()

    var descriptionAdded = false
    var placeAdded = false
    var personAdded = false
    var deadlineAdded = false
    var checklistAdded = false

    var newTask : Task? = null


    fun createTask(){
        createTaskRepository.createTask()
    }

    fun prepareTask(){
        newTask = Task(0,"",Importance.IMPORTANT,"",0.0,0.0,0.0, arrayListOf(), arrayListOf())
    }

    fun setDescription(words : String){
        newTask!!.description = words
    }

    protected fun finalize(){
        createTaskRepository.releaseStatus()
    }
}