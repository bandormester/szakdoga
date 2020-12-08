package hu.szurdok.todoapp.data.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.TaskCard
import hu.szurdok.todoapp.data.room.TaskCardDao
import hu.szurdok.todoapp.retrofit.TaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class BrowseTasksRepository(
    private val taskService: TaskService,
    private val taskCardDao : TaskCardDao,
    private val executor: Executor
) {
    fun getTasks(groupId : Int) :LiveData<List<TaskCard>>{
        refreshTasks(groupId)
        return taskCardDao.load()
    }

    fun getMyTasks(token: ApiToken, groupId: Int) : LiveData<List<TaskCard>>{
        refreshMyTasks(token.id, groupId)
        return taskCardDao.load()
    }

    fun getDoneTasks(token: ApiToken, groupId: Int) : LiveData<List<TaskCard>>{
        refreshDoneTasks(token.id, groupId)
        return taskCardDao.load()
    }

    private fun refreshTasks(groupId: Int){
        taskService.getTasks(groupId).enqueue(object : Callback<List<TaskCard>> {
            override fun onResponse(call: Call<List<TaskCard>>, response: Response<List<TaskCard>>) {
                if (response.isSuccessful) {
                    executor.execute{
                        taskCardDao.clearTable()
                        taskCardDao.saveAll(response.body()!!)
                    }
                } else{
                    Log.d("Browse", response.message())
                    Log.d("Browse", response.toString())
                }
            }

            override fun onFailure(call: Call<List<TaskCard>>, t: Throwable) {
                Log.d("Browse", t.message.toString())
                Log.d("Browse", t.toString())
            }

        })
    }

    private fun refreshMyTasks(userId : Int, groupId: Int){
                taskService.getMyTasks(userId, groupId).enqueue(object : Callback<List<TaskCard>> {
                override fun onResponse(call: Call<List<TaskCard>>, response: Response<List<TaskCard>>) {
                    if (response.isSuccessful) {
                        executor.execute{
                            taskCardDao.clearTable()
                            taskCardDao.saveAll(response.body()!!)
                        }
                    } else{
                        Log.d("Browse", response.message())
                        Log.d("Browse", response.toString())
                    }
                }

                override fun onFailure(call: Call<List<TaskCard>>, t: Throwable) {
                    Log.d("Browse", t.message.toString())
                    Log.d("Browse", t.toString())
                }
            })
    }

    private fun refreshDoneTasks(userId : Int, groupId: Int){
        taskService.getDoneTasks(userId, groupId).enqueue(object : Callback<List<TaskCard>> {
                override fun onResponse(call: Call<List<TaskCard>>, response: Response<List<TaskCard>>) {
                    if (response.isSuccessful) {
                        executor.execute{
                            taskCardDao.clearTable()
                            taskCardDao.saveAll(response.body()!!)
                        }
                    } else{
                        Log.d("Browse", response.message())
                        Log.d("Browse", response.toString())
                    }
                }

                override fun onFailure(call: Call<List<TaskCard>>, t: Throwable) {
                    Log.d("Browse", t.message.toString())
                    Log.d("Browse", t.toString())
                }

            })
    }
}