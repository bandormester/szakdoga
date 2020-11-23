package hu.szurdok.todoapp.data.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.Group
import hu.szurdok.todoapp.data.room.GroupDao
import hu.szurdok.todoapp.data.models.User
import hu.szurdok.todoapp.retrofit.GroupService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class GroupDetailsRepository(
    private val groupService : GroupService,
    private val groupDao : GroupDao,
    private val executor: Executor
) {
    private var members : MutableLiveData<List<User>>? = null
    private var removeMessage : MutableLiveData<String>? = null

    fun getRemoveMessage() : MutableLiveData<String> {
        removeMessage = MutableLiveData()
        return removeMessage!!
    }

    fun releaseRemoveMessage(){
        removeMessage = null
    }

    fun getGroup(id : Int) : LiveData<Group>{
        updateGroup(id)
        return groupDao.loadById(id)
    }

    private fun updateGroup(id : Int){
        executor.execute{
            val response = groupService.getGroup(id).execute()
            if(response.isSuccessful){
                groupDao.save(response.body()!!)
            } else Log.d("retrofit", "sikertelen getGroup")
        }
    }

    fun getMembers(id : Int) : MutableLiveData<List<User>>{
        members = MutableLiveData(emptyList())
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

    fun removeMember(groupId : Int, token : ApiToken, removedId : Int) {
        groupService.removeGroupMember(groupId, removedId, token.id).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    updateMembers(groupId)
                    removeMessage!!.value = response.body()
                }
                else{
                    removeMessage!!.value = "Remove failed"
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                removeMessage!!.value = "Failed to communicate with server"
            }

        })
    }
}