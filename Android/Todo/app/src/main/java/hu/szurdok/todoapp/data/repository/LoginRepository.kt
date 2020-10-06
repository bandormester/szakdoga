package hu.szurdok.todoapp.data.repository

import android.util.Log
import hu.szurdok.todoapp.retrofit.LoginService
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class LoginRepository (
    private val loginService : LoginService
){

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

    fun register(picture : ByteArray?, fullname : String, username : String, email : String, password : String){
        val requestBody : RequestBody = if(picture == null){
            RequestBody.create(null,"")
        }else
            RequestBody.create(MediaType.parse("application/octet-stream"), picture)

        loginService.register(requestBody, fullname, username, email, password).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Log.d("Register", response.body().toString())
                    Log.d("Register", response.message().toString())
                    Log.d("Register", response.code().toString())
                    Log.d("Register", response.body())
                }else{
                    Log.d("Register", response.body().toString())
                    Log.d("Register", response.message().toString())
                    Log.d("Register", response.code().toString())
                    Log.d("Register", response.errorBody().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("Register", t.message.toString())
                Log.d("Register", t.cause.toString())
                Log.d("Register", "Failed")
            }

        })
    }
}