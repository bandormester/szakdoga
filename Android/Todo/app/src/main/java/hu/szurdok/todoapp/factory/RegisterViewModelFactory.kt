package hu.szurdok.todoapp.factory

import hu.szurdok.todoapp.data.repository.login.RegisterRepositry
import hu.szurdok.todoapp.viewmodel.login.RegisterViewModel

class RegisterViewModelFactory(private val registerRepositry: RegisterRepositry) :
    Factory<RegisterViewModel> {
    override fun create(): RegisterViewModel {
        return RegisterViewModel(registerRepositry)
    }
}