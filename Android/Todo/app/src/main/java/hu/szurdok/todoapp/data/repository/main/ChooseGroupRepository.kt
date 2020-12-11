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
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.Group
import hu.szurdok.todoapp.data.room.GroupDao
import hu.szurdok.todoapp.retrofit.GroupService
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.util.concurrent.Executor

class ChooseGroupRepository(
    private val chooseGroupService: GroupService,
    private val groupDao : GroupDao,
    private val executor : Executor
) {
    private var createStatus : MutableLiveData<String>? = null

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
                    refreshGroups(token)
                }else{
                    createStatus?.value = response.body()
                    Log.d("Join", response.body().toString())
                    Log.d("Join", response.code().toString())
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("Join", t.message.toString())
                Log.d("Join", t.cause.toString())
                Log.d("Join", "Failed")
                createStatus?.value = "Unable to connect server"
            }
        })
    }

    private fun refreshGroups(token : ApiToken) {
            chooseGroupService.getMyGroups(token.id).enqueue(object : Callback<List<Group>>{
                override fun onResponse(call: Call<List<Group>>, response: Response<List<Group>>) {
                    if (response.isSuccessful) {
                        executor.execute {
                            groupDao.clear()
                            groupDao.saveAll(response.body()!!)
                        }
                    } else Log.d("Join", "sikertelen")
                }

                override fun onFailure(call: Call<List<Group>>, t: Throwable) {
                    Log.d("Join", "sikertelen")
                }

            })
    }

    fun getGroupPicture(id : Int, imageView : ImageView, context: Context){
        val glideUrl = GlideUrl("http://84.0.25.32:8080/group/$id/pic")
        val option = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)
        Glide.with(context)
            .load(glideUrl)
            .apply(option)
            .into(imageView)
    }

    fun joinGroup(code : String, userId : Int){
        executor.execute{
            try{
                chooseGroupService.joinGroup(code, userId).execute()
            }catch (e : SocketTimeoutException){
                Log.d("Join", "timeout")
            }
        }
    }
}