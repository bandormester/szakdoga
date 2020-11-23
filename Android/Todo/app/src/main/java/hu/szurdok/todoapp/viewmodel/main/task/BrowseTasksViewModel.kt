package hu.szurdok.todoapp.viewmodel.main.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.repository.main.BrowseTasksRepository
import hu.szurdok.todoapp.data.models.TaskCard

class BrowseTasksViewModel (
    private val browseTasksRepository: BrowseTasksRepository,
    private val token : ApiToken,
    private val groupId : Int
) : ViewModel() {
    var tasks : LiveData<List<TaskCard>> = browseTasksRepository.getTasks(groupId)

    fun getTasks(){
        tasks = browseTasksRepository.getTasks(groupId)
    }

    fun getMyTasks(){
        tasks = browseTasksRepository.getMyTasks(token, groupId)
    }

    fun getDoneTasks(){
        tasks = browseTasksRepository.getDoneTasks(token, groupId)
    }
}