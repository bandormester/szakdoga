package hu.szurdok.todoapp.container

import android.content.Context
import androidx.room.Room
import hu.szurdok.todoapp.container.factory.UserViewModelFactory
import hu.szurdok.todoapp.data.UserDao
import hu.szurdok.todoapp.data.UserDatabase
import hu.szurdok.todoapp.data.repository.UserRepository
import hu.szurdok.todoapp.retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppContainer (){
    var context : Context? = null

    private lateinit var userDao : UserDao
    private lateinit var userService : UserService
    private lateinit var executor : Executor
    lateinit var userRepository: UserRepository
    lateinit var userViewModelFactory : UserViewModelFactory

    fun setupContainer(context: Context){
        userDao =
            Room.databaseBuilder(context, UserDatabase::class.java, "user-db").build().userDao()

        userService =
            Retrofit.Builder().baseUrl("http://10.0.2.2:8080").addConverterFactory(GsonConverterFactory.create()).build().create(UserService::class.java)

        executor =
            Executors.newCachedThreadPool()

        userRepository = UserRepository(executor, userDao, userService)

        userViewModelFactory = UserViewModelFactory(userRepository)
    }

    var userContainer : UserContainer? = null

    private var loginContainer : LoginContainer? = null

    fun getLoginContainer() : LoginContainer{
        if(loginContainer == null){
            loginContainer = LoginContainer()
        }
        return loginContainer!!
    }

    fun releaseLoginContainer(){
        loginContainer = null
    }
}