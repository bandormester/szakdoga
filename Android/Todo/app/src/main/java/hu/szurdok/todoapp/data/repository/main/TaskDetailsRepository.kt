package hu.szurdok.todoapp.data.repository.main

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import hu.szurdok.todoapp.data.models.Task
import hu.szurdok.todoapp.data.room.TaskDao
import hu.szurdok.todoapp.retrofit.TaskService
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.Executor

class TaskDetailsRepository(
    private val taskService: TaskService,
    private val taskDao : TaskDao,
    private val executor: Executor
) {
    fun getTask(taskId : Int) : LiveData<Task?> {
        refreshTask(taskId)
        return taskDao.loadById(taskId)
    }

    private fun refreshTask(taskId: Int){
            executor.execute{
                try{
                    val response = taskService.getTaskById(taskId).execute()
                    if (response.isSuccessful) {
                        taskDao.save(response.body()!!)
                    } else {
                        Log.d("Details", response.message())
                        Log.d("Details", response.toString())
                    }
                } catch(e : SocketTimeoutException){
                    Log.d("Details","timeout")
                } catch(e : SocketException){
                    Log.d("Details","socket exception")
                }
            }
    }

    fun check(checkId: Int, done: Boolean) {
        executor.execute {
            try {
                val response = taskService.checkItem(checkId, done).execute()
                if (response.isSuccessful) {
                    Log.d("Details", "Success")
                } else {
                    Log.d("Details", "Failure")
                }
            } catch (e: SocketTimeoutException) {
                Log.d("Details", "timeout")
            }
        }
    }

    fun finishTask(taskId: Int) {
        executor.execute{
            try{
                val response = taskService.finishTask(taskId).execute()
                if(response.isSuccessful){
                    taskDao.delete(taskId)
                    Log.d("Details", "Success")
                }else{
                    Log.d("Details", "Failure")
                }
            } catch(e : SocketTimeoutException){
                Log.d("Details", "timeout")
            }
        }
        
    }

    fun getPicture(id : Int, imageView : ImageView, context: Context){
            val glideUrl = GlideUrl("http://84.0.25.32:8080/user/$id/pic")
            val option = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
            Glide.with(context)
                .load(glideUrl)
                .apply(option)
                .into(imageView)

    }
}