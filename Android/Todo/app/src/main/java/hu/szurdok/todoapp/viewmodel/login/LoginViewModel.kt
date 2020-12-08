package hu.szurdok.todoapp.viewmodel.login

import androidx.lifecycle.*
import com.google.common.hash.Hashing
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.repository.login.LoginRepository
import java.nio.charset.StandardCharsets

class LoginViewModel (
    private val loginRepository: LoginRepository
) : ViewModel() {
    val token : MutableLiveData<ApiToken> = loginRepository.getLastToken()

    fun tryLogin(username : String, password : String) {
        val encPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()
        loginRepository.tryLogin(username, encPassword)
    }

    protected fun finalize(){
        loginRepository.releaseToken()
    }
}