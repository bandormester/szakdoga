package hu.szurdok.todoapp.retrofit

import hu.szurdok.todoapp.data.models.misc.RegistrationStatus
import hu.szurdok.todoapp.data.models.Task
import hu.szurdok.todoapp.data.models.TaskCard
import retrofit2.Call
import retrofit2.http.*

interface TaskService {

    @POST("/task")
    fun createTask(
        @Body task : Task,
        @Query("hasAssignee") hasAssignee : Boolean,
        @Query("hasChecklist") hasChecklist : Boolean
    ) : Call<RegistrationStatus>

    @GET("/task/{userId}")
    fun getMyTasks(
        @Path("userId") userId : Int,
        @Query("groupId") groupId : Int
    ) : Call<List<TaskCard>>

    @GET("/task")
    fun getTasks(
        @Query("groupId") groupId : Int
    ) : Call<List<TaskCard>>

    @GET("/task/{userId}/done")
    fun getDoneTasks(
        @Path("userId") userId : Int,
        @Query("groupId") groupId : Int
    ) : Call<List<TaskCard>>

    @GET("/task/id/{taskId}")
    fun getTaskById(
        @Path("taskId") taskId : Int
    ) : Call<Task>

    @PUT("/task/check/{checkId}")
    fun checkItem(
        @Path("checkId") checkId : Int,
        @Query("isChecked") isChecked : Boolean
    ) : Call<Void>

    @DELETE("/task/id/{taskId}")
    fun finishTask(
        @Path("taskId") taskId : Int
    ) : Call<Void>

    @GET("/user/{userId}/pic")
    fun getPicture(
        @Path("userId") userId: Int
    ) : Call<Array<Byte>>
}