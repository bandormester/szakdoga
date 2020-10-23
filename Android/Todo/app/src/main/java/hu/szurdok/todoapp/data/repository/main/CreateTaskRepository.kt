package hu.szurdok.todoapp.data.repository.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.szurdok.todoapp.data.RegistrationStatus
import hu.szurdok.todoapp.retrofit.TaskService

class CreateTaskRepository(
    private val taskService : TaskService
) {

    private var creationStatus : MutableLiveData<RegistrationStatus>? = null

    fun subscribeStatus() : LiveData<RegistrationStatus> {
        creationStatus = MutableLiveData()
        return creationStatus!!
    }

    fun releaseStatus(){
        creationStatus = null
    }

    fun createTask() {

    }
}