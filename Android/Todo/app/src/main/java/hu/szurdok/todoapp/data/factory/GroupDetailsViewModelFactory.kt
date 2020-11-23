package hu.szurdok.todoapp.data.factory

import android.content.Context
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.repository.main.GroupDetailsRepository
import hu.szurdok.todoapp.viewmodel.main.group.GroupDetailsViewModel

class GroupDetailsViewModelFactory(private val groupDetailsRepository: GroupDetailsRepository)  {
    fun create(token : ApiToken, groupdId : Int, context : Context): GroupDetailsViewModel {
        return GroupDetailsViewModel(groupDetailsRepository, token, groupdId, context)
    }
}