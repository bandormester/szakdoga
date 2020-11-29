package hu.szurdok.todoapp.data.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import hu.szurdok.todoapp.data.models.Task
import hu.szurdok.todoapp.data.models.misc.Importance
import hu.szurdok.todoapp.data.room.TaskDao
import hu.szurdok.todoapp.retrofit.TaskService
import java.net.SocketTimeoutException
import java.util.concurrent.Executor
import java.util.concurrent.TimeoutException

class TaskDetailsRepository(
    private val taskService: TaskService,
    private val taskDao : TaskDao,
    private val executor: Executor
) {
    fun getTask(taskId : Int) : LiveData<Task?> {
        //if(!taskDao.isRowExists(taskId)){
        //    taskDao.save(Task(taskId, 0, 0, "Label", Importance.IMPORTANT, null, null, null, null, null))
        //}
        Log.d("retrofit", taskId.toString())
        refreshTask(taskId)
        return taskDao.loadById(taskId)
    }

    private fun refreshTask(taskId: Int){
        try{
            executor.execute{
                val response = taskService.getTaskById(taskId).execute()
                if (response.isSuccessful) {
                    taskDao.save(response.body()!!)
                    Log.d("retrofit", "sikeres")
                } else {
                    Log.d("retrofit", response.message())
                    Log.d("retorift", response.toString())
                }
            }
        } catch(e : SocketTimeoutException){
            Log.d("retrofit","timeout, retrying")
            refreshTask(taskId)
        }
    }

    fun check(checkId: Int, done: Boolean) {
        executor.execute{
            val response = taskService.checkItem(checkId, done).execute()
            if(response.isSuccessful){
                Log.d("retrofit", response.message())
            }else{
                Log.d("retrofit", response.message())
            }
        }
    }
}