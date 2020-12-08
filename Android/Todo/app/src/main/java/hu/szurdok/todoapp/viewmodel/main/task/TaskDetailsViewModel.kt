package hu.szurdok.todoapp.viewmodel.main.task

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import hu.szurdok.todoapp.data.models.Check
import hu.szurdok.todoapp.data.repository.main.TaskDetailsRepository
import hu.szurdok.todoapp.data.models.Task
import hu.szurdok.todoapp.viewmodel.main.ImageLoadingViewModel

class TaskDetailsViewModel(
    private val taskDetailsRepository: TaskDetailsRepository,
    private val taskId : Int
): ImageLoadingViewModel  {
    fun checkItem(check: Check) {
        taskDetailsRepository.check(check.id, check.done)
    }

    fun finishTask(){
        taskDetailsRepository.finishTask(taskId)
    }

    val task : LiveData<Task?> = taskDetailsRepository.getTask(taskId)

    override fun getPicture(id: Int, imageView: ImageView, context: Context) {
        taskDetailsRepository.getPicture(id, imageView, context)
    }
}
