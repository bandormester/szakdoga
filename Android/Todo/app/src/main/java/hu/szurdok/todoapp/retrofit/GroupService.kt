package hu.szurdok.todoapp.retrofit

import hu.szurdok.todoapp.data.models.Group
import hu.szurdok.todoapp.data.models.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface GroupService {
    @GET("/group/user/{userId}")
    fun getMyGroups(@Path("userId") userId : Int) : Call<List<Group>>

    @GET("/group/{groupId}")
    fun getGroup(@Path("groupId") groupId : Int) : Call<Group>

    @GET("/group/{groupId}/members")
    fun getGroupMembers(@Path("groupId") groupId: Int) : Call<List<User>>

    @DELETE("/group/{groupId}/members")
    fun removeGroupMember(
        @Path("groupId") groupId : Int,
        @Query("removedId") removedId : Int,
        @Query("userId") userId : Int) : Call<String>

    @POST("/group")
    fun createGroup(
        @Body picture : RequestBody,
        @Query("groupName") groupName : String,
        @Query("ownerId") ownerId : Int,
        @Query("description") description : String,
        @Query("joinCode") joinCode : String,
        @Query("hasPicture") hasPicture : Boolean
        ) : Call<String>

    @PUT("/group")
    fun joinGroup(
        @Query("code") code: String,
        @Query("userId") userId: Int
    ) : Call<Void>
}