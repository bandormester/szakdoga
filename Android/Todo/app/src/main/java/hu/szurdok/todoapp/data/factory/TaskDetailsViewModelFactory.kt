package hu.szurdok.todoapp.data.factory

import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.repository.main.TaskDetailsRepository
import hu.szurdok.todoapp.viewmodel.main.task.TaskDetailsViewModel

class TaskDetailsViewModelFactory(private val taskDetailsRepository: TaskDetailsRepository) {
    fun create(taskId : Int): TaskDetailsViewModel {
        return TaskDetailsViewModel(taskDetailsRepository, taskId)
    }
}