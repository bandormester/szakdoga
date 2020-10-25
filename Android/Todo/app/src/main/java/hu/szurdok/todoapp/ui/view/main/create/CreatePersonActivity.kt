package hu.szurdok.todoapp.ui.view.main.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.retrofit.GroupService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CreatePersonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_create_person)
//
       // val executor = Executors.newSingleThreadExecutor()
//
       // val gson = GsonBuilder().setLenient().create()
       // val groupService =
       //     Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
       //         .addConverterFactory(ScalarsConverterFactory.create())
       //         .addConverterFactory(GsonConverterFactory.create(gson))
       //         .build().create(GroupService::class.java)
//
       // executor.execute{
       //     val response = groupService.getGroupMembers(id).execute()
       //     if(response.isSuccessful){
       //         members!!.postValue(response.body()!!)
       //     }
       // }
    }


}