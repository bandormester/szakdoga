package hu.szurdok.todoapp.data.factory

import hu.szurdok.todoapp.data.repository.login.RegisterRepositry
import hu.szurdok.todoapp.viewmodel.login.RegisterViewModel

class RegisterViewModelFactory(private val registerRepository: RegisterRepositry) {
    fun create(): RegisterViewModel {
        return RegisterViewModel(registerRepository)
    }
}