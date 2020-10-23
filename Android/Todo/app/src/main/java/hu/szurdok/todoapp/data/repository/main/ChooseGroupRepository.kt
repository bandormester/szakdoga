package hu.szurdok.todoapp.data.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.group.Group
import hu.szurdok.todoapp.data.group.GroupDao
import hu.szurdok.todoapp.data.group.GroupDao_Impl
import hu.szurdok.todoapp.retrofit.GroupService
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ChooseGroupRepository(
    private val chooseGroupService: GroupService,
    private val groupDao : GroupDao
) {
    fun getGroups (token : ApiToken) : LiveData<List<Group>> {
        refreshGroups(token)
        return groupDao.load()
    }

    private fun refreshGroups(token : ApiToken) {
        val executor =
            Executors.newCachedThreadPool()
        executor.execute{
            val response = chooseGroupService.getMyGroups(token.id).execute()
            if (response.isSuccessful) {
                groupDao.saveAll(response.body()!!)
                Log.d("retrofit", response.body()!!.size.toString())
            } else Log.d("retrofit", "sikertelen")
        }

        //chooseGroupService.getMyGroups(token.id).enqueue(object : Callback<List<Group>>{
        //    override fun onResponse(call: Call<List<Group>>, response: Response<List<Group>>) {
        //        if(response.isSuccessful){
        //            Log.d("retrofit", "sikeres")
        //            if(response.body()!!.isNotEmpty())
        //                groupDao.saveAll(response.body()!!)
        //                Log.d("retrofit", response.body()!![0].toString())
        //        }
        //        Log.d("retrofit","sikertelen")
//
        //    }
//
        //    override fun onFailure(call: Call<List<Group>>, t: Throwable) {
        //        Log.d("retrofit", "fail")
        //        Log.d("retrofit", t.message)
        //    }
        //})
    }
}