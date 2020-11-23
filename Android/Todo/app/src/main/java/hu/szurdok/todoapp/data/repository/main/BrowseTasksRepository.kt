package hu.szurdok.todoapp.data.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.TaskCard
import hu.szurdok.todoapp.data.room.TaskCardDao
import hu.szurdok.todoapp.retrofit.TaskService
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
        executor.execute{
            val response = taskService.getTasks(groupId).execute()
            if (response.isSuccessful) {
                taskCardDao.clearTable()
                taskCardDao.saveAll(response.body()!!)
                Log.d("retrofit", response.body()!![0].id.toString())
            } else{
                Log.d("retrofit", response.message())
                Log.d("retorift", response.toString())
            }
        }
    }

    private fun refreshMyTasks(userId : Int, groupId: Int){
        executor.execute{
            val response = taskService.getMyTasks(userId, groupId).execute()
            if (response.isSuccessful) {
                taskCardDao.clearTable()
                taskCardDao.saveAll(response.body()!!)
                Log.d("retrofit", response.body()!!.size.toString())
            } else {
                Log.d("retrofit", response.message())
                Log.d("retorift", response.toString())
            }
        }
    }

    private fun refreshDoneTasks(userId : Int, groupId: Int){
        executor.execute{
            val response = taskService.getDoneTasks(userId, groupId).execute()
            if (response.isSuccessful) {
                taskCardDao.clearTable()
                taskCardDao.saveAll(response.body()!!)
                Log.d("retrofit", response.body()!!.size.toString())
            } else {
                Log.d("retrofit", response.message())
                Log.d("retorift", response.toString())
            }
        }
    }
}