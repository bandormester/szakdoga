package hu.szurdok.todoapp.retrofit

import hu.szurdok.todoapp.data.group.Group
import hu.szurdok.todoapp.data.user.User
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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
}