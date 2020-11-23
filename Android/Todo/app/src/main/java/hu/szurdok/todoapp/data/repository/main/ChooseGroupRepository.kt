package hu.szurdok.todoapp.data.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.Group
import hu.szurdok.todoapp.data.room.GroupDao
import hu.szurdok.todoapp.retrofit.GroupService
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class ChooseGroupRepository(
    private val chooseGroupService: GroupService,
    private val groupDao : GroupDao
) {
    private var createStatus : MutableLiveData<String>? = null
    val executor =
        Executors.newCachedThreadPool()

    fun getGroups (token : ApiToken) : LiveData<List<Group>> {
        refreshGroups(token)
        return groupDao.load()
    }

    fun getStatus() : LiveData<String>{
        createStatus = MutableLiveData()
        return createStatus!!
    }

    fun releaseStatus(){
        createStatus = null
    }

    fun getPicture(groupId : Int) {
        executor.execute{
            val response = chooseGroupService.getPicture(groupId)
            //TODO
        }
    }

    fun createGroup(picture : ByteArray?, token : ApiToken, name : String, description : String, joinCode : String, hasPicture : Boolean){
        val requestBody : RequestBody = if(hasPicture){
            RequestBody.create(MediaType.parse("application/octet-stream"), picture!!)
        } else {
            RequestBody.create(null,"")
        }

        chooseGroupService.createGroup(requestBody, name, token.id, description, joinCode, hasPicture).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    createStatus?.value = response.body()
                    Log.d("vm",response.body()!!.toString())
                    refreshGroups(token)
                }else{
                    createStatus?.value = response.body()
                    Log.d("Register", response.body().toString())
                    Log.d("Register", response.code().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("Register", t.message.toString())
                Log.d("Register", t.cause.toString())
                Log.d("Register", "Failed")
                createStatus?.value = "Unable to connect server"
            }
        })
    }

    private fun refreshGroups(token : ApiToken) {
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