package hu.szurdok.todoapp.viewmodel

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.szurdok.todoapp.data.RegistrationStatus
import hu.szurdok.todoapp.data.User
import hu.szurdok.todoapp.data.repository.LoginRepository
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream

class LoginViewModel (
    private val loginRepository: LoginRepository
) : ViewModel() {
    private var picture : ByteArray? = null
    val registrationStatus: LiveData<RegistrationStatus> = loginRepository.getStatus()

    fun tryLogin(username : String, password : String) : Boolean{
        val encPassword = Base64.encodeToString(password.toByteArray(Charsets.UTF_8), Base64.NO_WRAP)
        return loginRepository.tryLogin(username, encPassword)
    }

    fun register(fullname : String, username : String, email : String, password : String){
        if(picture == null){
            loginRepository.registerNoPic(fullname, username, email, password)
        }else
            loginRepository.register(picture, fullname, username, email, password)
    }

    fun setPicture(bitmap : Bitmap){
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        picture = stream.toByteArray()
    }
}