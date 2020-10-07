package hu.szurdok.todoapp.retrofit

import hu.szurdok.todoapp.data.RegistrationStatus
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @GET("/user/login")
    fun tryLogin(username : String, password : String) : Call<Boolean>

    @POST("/user/register")
    fun register(
        @Body picture : RequestBody,
        @Query("fullname") fullname : String,
        @Query("username") username : String,
        @Query("email") email : String,
        @Query("password") password : String) : Call<RegistrationStatus>

    @POST("/user/register/nopic")
    fun registerNoPic(
        @Query("fullname") fullname : String,
        @Query("username") username : String,
        @Query("email") email : String,
        @Query("password") password : String) : Call<RegistrationStatus>
}