package hu.szurdok.todoapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import hu.szurdok.todoapp.data.RegistrationStatus
import hu.szurdok.todoapp.retrofit.LoginService
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository (
    private val loginService : LoginService
){

    private val status = MutableLiveData<RegistrationStatus>(RegistrationStatus(false,""))

    fun getStatus() : MutableLiveData<RegistrationStatus>{
        return status;
    }

    fun tryLogin(username : String, password : String) : Boolean {
        loginService.tryLogin(username, password).enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                Log.d("Login", "onResponse")
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.d("Login", "onFailure")
            }
        })
        return true
    }

    fun register(picture : ByteArray?, fullname : String, username : String, email : String, password : String) {
        val requestBody : RequestBody = if(picture == null){
            RequestBody.create(null,"")
        }else
            RequestBody.create(MediaType.parse("application/octet-stream"), picture)

        loginService.register(requestBody, fullname, username, email, password).enqueue(object : Callback<RegistrationStatus>{
            override fun onResponse(call: Call<RegistrationStatus>, response: Response<RegistrationStatus>) {
                if (response.isSuccessful) {
                    status.value = response.body()
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
                status.value = RegistrationStatus(false, "Unable to connect server")
            }
        })
    }

    fun registerNoPic(fullname : String, username : String, email : String, password : String) {
        loginService.registerNoPic(fullname, username, email, password).enqueue(object : Callback<RegistrationStatus>{
            override fun onResponse(call: Call<RegistrationStatus>, response: Response<RegistrationStatus>) {
                if (response.isSuccessful) {
                    status.value = response.body()
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
                status.value = RegistrationStatus(false, "Unable to connect server")
            }

        })
    }
}