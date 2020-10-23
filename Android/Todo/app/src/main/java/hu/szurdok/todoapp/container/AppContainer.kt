package hu.szurdok.todoapp.container

import android.content.Context
import androidx.room.Room
import hu.szurdok.todoapp.TodoApplication
import hu.szurdok.todoapp.data.group.GroupDatabase
import hu.szurdok.todoapp.factory.UserViewModelFactory
import hu.szurdok.todoapp.data.user.UserDao
import hu.szurdok.todoapp.data.user.UserDatabase
import hu.szurdok.todoapp.data.repository.UserRepository
import hu.szurdok.todoapp.retrofit.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppContainer (val app : TodoApplication){
    private lateinit var userDao : UserDao
    private lateinit var userService : UserService
    lateinit var userRepository: UserRepository
    lateinit var userViewModelFactory : UserViewModelFactory

    fun setupContainer(context: Context){
        userDao =
            Room.databaseBuilder(context, UserDatabase::class.java, "user-db").build().userDao()

        userService =
            Retrofit.Builder().baseUrl("http://10.0.2.2:8080").addConverterFactory(GsonConverterFactory.create()).build().create(UserService::class.java)

        userRepository = UserRepository(executor, userDao, userService)

        userViewModelFactory = UserViewModelFactory(userRepository)
    }

    private var executor =
    Executors.newCachedThreadPool()

    var userContainer : UserContainer? = null

    private var loginContainer : LoginContainer? = null

    private var mainContainer : MainContainer? = null

    fun getMainContainer() : MainContainer{
        mainContainer = MainContainer(app)
        return mainContainer!!
    }

    fun releaseMainContainer(){
        mainContainer = null
    }

    fun getLoginContainer() : LoginContainer{
        loginContainer = LoginContainer(app)
        return loginContainer!!
    }

    fun releaseLoginContainer(){
        loginContainer = null
    }
}