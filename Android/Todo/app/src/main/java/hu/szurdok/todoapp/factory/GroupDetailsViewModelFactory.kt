package hu.szurdok.todoapp.factory

import android.content.Context
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.repository.main.GroupDetailsRepository
import hu.szurdok.todoapp.viewmodel.main.GroupDetailsViewModel

class GroupDetailsViewModelFactory(private val groupDetailsRepository: GroupDetailsRepository)  {
    fun create(token : ApiToken, groupdId : Int, context : Context): GroupDetailsViewModel {
        return GroupDetailsViewModel(groupDetailsRepository, token, groupdId, context)
    }
}