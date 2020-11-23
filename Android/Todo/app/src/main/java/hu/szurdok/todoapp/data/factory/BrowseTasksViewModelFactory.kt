package hu.szurdok.todoapp.data.factory

import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.repository.main.BrowseTasksRepository
import hu.szurdok.todoapp.viewmodel.main.task.BrowseTasksViewModel

class BrowseTasksViewModelFactory (private val browseTasksRepository: BrowseTasksRepository)  {
    fun create(token : ApiToken, groupId : Int): BrowseTasksViewModel {
        return BrowseTasksViewModel(browseTasksRepository, token, groupId)
    }
}