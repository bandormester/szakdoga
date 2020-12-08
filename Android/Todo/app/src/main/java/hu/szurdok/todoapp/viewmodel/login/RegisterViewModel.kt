package hu.szurdok.todoapp.viewmodel.login

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.common.hash.Hashing
import hu.szurdok.todoapp.data.models.misc.RegistrationStatus
import hu.szurdok.todoapp.data.repository.login.RegisterRepositry
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets

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

        val codedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()

        registerRepository.register(picture, fullname, username, email, codedPassword, hasPicture)
    }

    protected fun finalize(){
        registerRepository.releaseStatus()
    }
}