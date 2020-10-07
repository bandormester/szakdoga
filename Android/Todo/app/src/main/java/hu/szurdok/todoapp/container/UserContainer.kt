package hu.szurdok.todoapp.container

import hu.szurdok.todoapp.factory.UserViewModelFactory
import hu.szurdok.todoapp.data.repository.UserRepository

class UserContainer(val userRepository: UserRepository) {
    val userViewModelFactory = UserViewModelFactory(userRepository)
}