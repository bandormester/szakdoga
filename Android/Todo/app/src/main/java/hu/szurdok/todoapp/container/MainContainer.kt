package hu.szurdok.todoapp.container

import androidx.room.Room
import com.google.gson.GsonBuilder
import hu.szurdok.todoapp.TodoApplication
import hu.szurdok.todoapp.data.group.GroupDatabase
import hu.szurdok.todoapp.data.repository.main.ChooseGroupRepository
import hu.szurdok.todoapp.data.repository.main.CreateTaskRepository
import hu.szurdok.todoapp.data.repository.main.GroupDetailsRepository
import hu.szurdok.todoapp.factory.ChooseGroupViewModelFactory
import hu.szurdok.todoapp.factory.CreateTaskViewModelFactory
import hu.szurdok.todoapp.factory.GroupDetailsViewModelFactory
import hu.szurdok.todoapp.retrofit.GroupService
import hu.szurdok.todoapp.retrofit.TaskService
import hu.szurdok.todoapp.viewmodel.main.CreateTaskViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.Executors

class MainContainer(app: TodoApplication) {

    private val gson = GsonBuilder().setLenient().create()

    private val groupService =
        Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(GroupService::class.java)

    private val taskService =
        Retrofit.Builder().baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(TaskService::class.java)

    private var groupDao = Room.databaseBuilder(app, GroupDatabase::class.java, "user-db").build().groupDao()

    private var executor = Executors.newCachedThreadPool()

    private val chooseGroupRepository = ChooseGroupRepository(groupService, groupDao)

    val chooseGroupViewModelFactory = ChooseGroupViewModelFactory(chooseGroupRepository)

    private val groupDetailsRepository = GroupDetailsRepository(groupService, groupDao, executor)

    val groupDetailsViewModelFactory = GroupDetailsViewModelFactory(groupDetailsRepository)

    private val createTaskRepository = CreateTaskRepository(taskService)

    val createTaskViewModelFactory = CreateTaskViewModelFactory(createTaskRepository)
}