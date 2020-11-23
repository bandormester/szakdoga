package hu.szurdok.todoapp.data.factory

import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.repository.main.ChooseGroupRepository
import hu.szurdok.todoapp.viewmodel.main.group.ChooseGroupViewModel

class ChooseGroupViewModelFactory(private val chooseGroupRepository: ChooseGroupRepository)  {
    fun create(token : ApiToken): ChooseGroupViewModel {
        return ChooseGroupViewModel(chooseGroupRepository, token)
    }
}