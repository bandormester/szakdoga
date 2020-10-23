package hu.szurdok.todoapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import hu.szurdok.todoapp.data.user.User
import hu.szurdok.todoapp.data.user.UserDao
import hu.szurdok.todoapp.retrofit.UserService
import java.util.concurrent.Executor
import javax.inject.Singleton

@Singleton
class UserRepository(
    private val executor: Executor,
    private val userDao: UserDao,
    private val userService : UserService
) {
    fun getUsers() : LiveData<List<User>> {
        refreshUser()
        return userDao.load()
    }

    private fun refreshUser(){
        executor.execute{
            Thread.sleep(2000)
            val response = userService.getUsers().execute()
            if(response.isSuccessful){
                userDao.save(response.body()!![0])
            }else Log.d("retrofit","Repository call failed")
        }
    }
}