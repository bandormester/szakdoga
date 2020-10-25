package hu.szurdok.todoapp.data.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.szurdok.todoapp.data.RegistrationStatus
import hu.szurdok.todoapp.data.task.Task
import hu.szurdok.todoapp.data.user.User
import hu.szurdok.todoapp.retrofit.GroupService
import hu.szurdok.todoapp.retrofit.TaskService
import okhttp3.RequestBody
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
        //taskService.createTask(task, hasChecklist, hasAssignee).enqueue(object : Callback<RegistrationStatus>{
        //    override fun onResponse(
        //        call: Call<RegistrationStatus>,
        //        response: Response<RegistrationStatus>
        //    ) {
        //        if(response.isSuccessful){
        //
        //        }
        //    }
//
        //    override fun onFailure(call: Call<RegistrationStatus>, t: Throwable) {
        //
        //    }
//
        //})

        Log.d("retro", task.toString())
    }

    fun getMembers(id : Int) : MutableLiveData<List<User>>{
        if(members == null) members = MutableLiveData(emptyList())
        updateMembers(id)
        return members!!
    }

    fun releaseMember(){
        members = null
    }

    private fun updateMembers(id : Int){
        executor.execute{
            val response = groupService.getGroupMembers(id).execute()
            if(response.isSuccessful){
                members!!.postValue(response.body()!!)
            }
        }
    }
}