package hu.szurdok.todoapp.factory

import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.repository.main.ChooseGroupRepository
import hu.szurdok.todoapp.viewmodel.main.ChooseGroupViewModel

class ChooseGroupViewModelFactory(private val chooseGroupRepository: ChooseGroupRepository)  {
    fun create(token : ApiToken): ChooseGroupViewModel {
        return ChooseGroupViewModel(chooseGroupRepository, token)
    }
}