package hu.szurdok.todoapp.data.factory

import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.repository.main.CreateTaskRepository
import hu.szurdok.todoapp.viewmodel.main.task.CreateTaskViewModel

class CreateTaskViewModelFactory(private val createTaskRepository: CreateTaskRepository)  {
    fun create(token : ApiToken, groupId : Int): CreateTaskViewModel {
        return CreateTaskViewModel(createTaskRepository, token, groupId)
    }
}