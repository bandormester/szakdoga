package hu.szurdok.todoapp.container

import com.google.gson.GsonBuilder
import hu.szurdok.todoapp.data.repository.LoginRepository
import hu.szurdok.todoapp.retrofit.LoginService
import hu.szurdok.todoapp.viewmodel.LoginViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class LoginContainer {

    private val gson = GsonBuilder().setLenient().create()

    private val loginService =
        Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(LoginService::class.java)

    private val loginRepository = LoginRepository(loginService)

    val loginViewModel = LoginViewModel(loginRepository)
}