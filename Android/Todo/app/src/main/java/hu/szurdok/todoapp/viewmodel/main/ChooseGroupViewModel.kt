package hu.szurdok.todoapp.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.group.Group
import hu.szurdok.todoapp.data.repository.main.ChooseGroupRepository

class ChooseGroupViewModel(
    private val chooseGroupRepository: ChooseGroupRepository,
    private val token : ApiToken
) : ViewModel() {
    val groups : LiveData<List<Group>> = chooseGroupRepository.getGroups(token)
}