package hu.szurdok.todoapp.retrofit

import hu.szurdok.todoapp.data.RegistrationStatus
import hu.szurdok.todoapp.data.task.Task
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface TaskService {

    @POST("/task")
    fun createTask(
        @Body task : Task,
        @Query("hasChecklist") hasChecklist : Boolean,
        @Query("hasAssignee") hasAssignee : Boolean) : Call<RegistrationStatus>

}