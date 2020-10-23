package hu.szurdok.todoapp.data.repository.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.szurdok.todoapp.data.RegistrationStatus
import hu.szurdok.todoapp.retrofit.LoginService
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepositry (
    private val loginService : LoginService
){
    private var status : MutableLiveData<RegistrationStatus>? = null

    fun getStatus() : MutableLiveData<RegistrationStatus> {
        status= MutableLiveData(RegistrationStatus(false,""))
        return status!!
    }

    fun releaseStatus() {
        status = null
    }

    fun register(picture : ByteArray?, fullname : String, username : String, email : String, password : String, hasPicture : Boolean) {
        val requestBody : RequestBody = if(hasPicture){
            RequestBody.create(MediaType.parse("application/octet-stream"), picture!!)
        } else {
            RequestBody.create(null,"")
        }

        loginService.register(requestBody, fullname, username, email, password, hasPicture).enqueue(object :
            Callback<RegistrationStatus> {
            override fun onResponse(call: Call<RegistrationStatus>, response: Response<RegistrationStatus>) {
                if (response.isSuccessful) {
                    status!!.value = response.body()
                    Log.d("vm",response.body()!!.successful.toString())
                }else{
                    Log.d("Register", response.body().toString())
                    Log.d("Register", response.code().toString())
                }
            }
            override fun onFailure(call: Call<RegistrationStatus>, t: Throwable) {
                Log.d("Register", t.message.toString())
                Log.d("Register", t.cause.toString())
                Log.d("Register", "Failed")
                status!!.value = RegistrationStatus(false, "Unable to connect server")
            }
        })
    }
}