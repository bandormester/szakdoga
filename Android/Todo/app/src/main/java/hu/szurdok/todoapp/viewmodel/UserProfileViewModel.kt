package hu.szurdok.todoapp.viewmodel

import androidx.lifecycle.*
import hu.szurdok.todoapp.data.User
import hu.szurdok.todoapp.data.repository.UserRepository

class UserProfileViewModel(
    userRepository: UserRepository
) : ViewModel() {
    val user : LiveData<List<User>> = userRepository.getUsers()
}