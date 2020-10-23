package hu.szurdok.todoapp.factory

import android.content.Context
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.repository.main.CreateTaskRepository
import hu.szurdok.todoapp.data.repository.main.GroupDetailsRepository
import hu.szurdok.todoapp.viewmodel.main.CreateTaskViewModel
import hu.szurdok.todoapp.viewmodel.main.GroupDetailsViewModel

class CreateTaskViewModelFactory(private val createTaskRepository: CreateTaskRepository)  {
    fun create(token : ApiToken, groupId : Int): CreateTaskViewModel {
        return CreateTaskViewModel(createTaskRepository, token, groupId)
    }
}