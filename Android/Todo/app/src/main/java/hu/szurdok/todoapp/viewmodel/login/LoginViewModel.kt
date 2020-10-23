package hu.szurdok.todoapp.viewmodel.login

import android.util.Base64
import androidx.lifecycle.*
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.repository.login.LoginRepository

class LoginViewModel (
    private val loginRepository: LoginRepository
) : ViewModel() {
    val token : MutableLiveData<ApiToken> = loginRepository.getLastToken()

    fun tryLogin(username : String, password : String) {
        val encPassword = Base64.encodeToString(password.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
        loginRepository.tryLogin(username, encPassword)
    }

    protected fun finalize(){
        loginRepository.releaseToken()
    }
}