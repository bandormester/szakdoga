package hu.szurdok.todoapp.retrofit

import hu.szurdok.todoapp.data.models.User
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("/user")
    fun getUsers() : Call<List<User>>
}