package hu.szurdok.todoapp.viewmodel.main.task

import androidx.lifecycle.LiveData
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.Check
import hu.szurdok.todoapp.data.repository.main.TaskDetailsRepository
import hu.szurdok.todoapp.data.models.Task

class TaskDetailsViewModel(
    private val taskDetailsRepository: TaskDetailsRepository,
    private val token: ApiToken,
    private val taskId : Int
)  {
    fun checkItem(check: Check) {
        taskDetailsRepository.check(check.id, check.done)
    }

    val task : LiveData<Task?> = taskDetailsRepository.getTask(taskId)
}
