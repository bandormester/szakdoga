package hu.szurdok.todoapp.data.repository.login

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.szurdok.todoapp.TodoApplication
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.retrofit.LoginService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository (
    private val loginService : LoginService,
    private val app : TodoApplication
){
    private val sharedPreferences: SharedPreferences = app.getSharedPreferences(app.SHARED_PREFENCES, MODE_PRIVATE)
    private var token : MutableLiveData<ApiToken>? = null

    fun getLastToken() : MutableLiveData<ApiToken> {
        token = MutableLiveData(ApiToken(0,""))

        val apiKey = sharedPreferences.getString(app.TOKEN_KEY, null)
        val userId = sharedPreferences.getInt(app.USERID_KEY, 0)

        if (apiKey != null && userId != 0) {
            token!!.value = ApiToken(userId, apiKey)
        }
        return token!!
    }

    fun releaseToken(){
        token = null
    }

    fun tryLogin(username : String, password : String) {
        loginService.tryLogin(username, password).enqueue(object : Callback<ApiToken>{
            override fun onResponse(call: Call<ApiToken>, response: Response<ApiToken>) {
                if(response.isSuccessful){
                    token!!.value = response.body()
                    if(response.body()!!.id != 0){
                        val editor = sharedPreferences.edit()
                        editor.putInt(app.USERID_KEY, response.body()!!.id)
                        editor.putString(app.TOKEN_KEY, response.body()!!.token)
                        editor.apply()
                    }
                }
                else{
                    token!!.value = ApiToken(0, "Login failed")
                }
            }

            override fun onFailure(call: Call<ApiToken>, t: Throwable) {
                token!!.value = ApiToken(0, "Unable to connect server")
            }
        })
    }
}