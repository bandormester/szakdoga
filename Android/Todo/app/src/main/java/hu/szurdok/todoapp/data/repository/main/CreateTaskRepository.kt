package hu.szurdok.todoapp.data.repository.main

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import hu.szurdok.todoapp.data.models.misc.RegistrationStatus
import hu.szurdok.todoapp.data.models.Task
import hu.szurdok.todoapp.data.models.User
import hu.szurdok.todoapp.retrofit.GroupService
import hu.szurdok.todoapp.retrofit.TaskService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class CreateTaskRepository(
    private val taskService : TaskService,
    private val groupService : GroupService,
    private val executor: Executor
) {

    private var creationStatus : MutableLiveData<RegistrationStatus>? = null
    private var members : MutableLiveData<List<User>>? = null

    fun subscribeStatus() : LiveData<RegistrationStatus> {
        creationStatus = MutableLiveData()
        return creationStatus!!
    }

    fun releaseStatus(){
        creationStatus = null
    }

    fun createTask(task : Task, hasAssignee: Boolean, hasChecklist: Boolean) {
        taskService.createTask(task, hasAssignee, hasChecklist).enqueue(object : Callback<RegistrationStatus>{
            override fun onResponse(
                call: Call<RegistrationStatus>,
                response: Response<RegistrationStatus>
            ) {
                if(response.isSuccessful){
                    Log.d("Create", response.message())
                    Log.d("Create", response.code().toString())
                    Log.d("Create", response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<RegistrationStatus>, t: Throwable) {
                Log.d("retrofit", t.message?:" NO MESSAGE ")
                Log.d("retrofit", t.cause.toString())
            }

        })
    }

    fun getMembers(id : Int) : MutableLiveData<List<User>>{
        if(members == null) members = MutableLiveData(emptyList())
        updateMembers(id)
        return members!!
    }

    fun getPicture(id : Int, imageView : ImageView, context: Context){
        val glideUrl = GlideUrl("http://84.0.25.32:8080/user/$id/pic")
        val option = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(context)
            .load(glideUrl)
            .apply(option)
            .into(imageView)
    }

    fun releaseMember(){
        members = null
    }

    private fun updateMembers(id : Int) {
        executor.execute {
            try {
                val response = groupService.getGroupMembers(id).execute()
                if (response.isSuccessful) {
                    members!!.postValue(response.body()!!)
                }
        } catch (e : Exception){
            Log.d("Create", "timeout")
        }
    }
    }
}