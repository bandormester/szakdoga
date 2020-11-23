package hu.szurdok.todoapp.viewmodel.login

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.szurdok.todoapp.data.models.misc.RegistrationStatus
import hu.szurdok.todoapp.data.repository.login.RegisterRepositry
import java.io.ByteArrayOutputStream

class RegisterViewModel (
    private val registerRepository: RegisterRepositry
) : ViewModel() {
    val registrationStatus: LiveData<RegistrationStatus> = registerRepository.getStatus()

    fun register(bitmap : Bitmap, fullname : String, username : String, email : String, password : String, hasPicture : Boolean){
        var picture: ByteArray? = null

        if(hasPicture){
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            picture = stream.toByteArray()
        }

        registerRepository.register(picture, fullname, username, email, password, hasPicture)
    }

    protected fun finalize(){
        registerRepository.releaseStatus()
    }
}