package hu.szurdok.todoapp.container.factory

import hu.szurdok.todoapp.data.repository.UserRepository
import hu.szurdok.todoapp.viewmodel.UserProfileViewModel

class UserViewModelFactory(private val userRepository: UserRepository) :
    Factory<UserProfileViewModel> {
    override fun create(): UserProfileViewModel {
        return UserProfileViewModel(userRepository)
    }
}