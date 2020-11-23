package hu.szurdok.todoapp.container

import androidx.room.Room
import com.google.gson.GsonBuilder
import hu.szurdok.todoapp.TodoApplication
import hu.szurdok.todoapp.data.room.GroupDatabase
import hu.szurdok.todoapp.data.repository.main.*
import hu.szurdok.todoapp.data.factory.*
import hu.szurdok.todoapp.retrofit.GroupService
import hu.szurdok.todoapp.retrofit.TaskService
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

    private var mainDatabase = Room.databaseBuilder(app, GroupDatabase::class.java, "user-db").build()

    private var groupDao = mainDatabase.groupDao()
    private var taskDao = mainDatabase.taskDao()
    private var taskCardDao = mainDatabase.taskCardDao()

    private var executor = Executors.newCachedThreadPool()

    private val chooseGroupRepository = ChooseGroupRepository(groupService, groupDao)

    val chooseGroupViewModelFactory = ChooseGroupViewModelFactory(chooseGroupRepository)

    private val groupDetailsRepository = GroupDetailsRepository(groupService, groupDao, executor)

    val groupDetailsViewModelFactory = GroupDetailsViewModelFactory(groupDetailsRepository)

    private val createTaskRepository = CreateTaskRepository(taskService, groupService, executor)

    val createTaskViewModelFactory = CreateTaskViewModelFactory(createTaskRepository)

    private val browseTasksRepository = BrowseTasksRepository(taskService, taskCardDao, executor)

    val browseTasksViewModelFactory = BrowseTasksViewModelFactory(browseTasksRepository)

    private val taskDetailsRepository = TaskDetailsRepository(taskService, taskDao, executor)

    val taskDetailsRepositoryFactory = TaskDetailsViewModelFactory(taskDetailsRepository)
}