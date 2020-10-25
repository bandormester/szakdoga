package hu.szurdok.todoapp.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.Importance
import hu.szurdok.todoapp.data.RegistrationStatus
import hu.szurdok.todoapp.data.check.Check
import hu.szurdok.todoapp.data.repository.main.CreateTaskRepository
import hu.szurdok.todoapp.data.task.Task
import hu.szurdok.todoapp.data.user.User

class CreateTaskViewModel(
    private val createTaskRepository: CreateTaskRepository,
    private val token : ApiToken,
    private val groupId : Int
) : ViewModel() {
    val creationStatus : LiveData<RegistrationStatus> = createTaskRepository.subscribeStatus()
    lateinit var members : LiveData<List<User>>

    var descriptionAdded = false
    var placeAdded = false
    var personAdded = false
    var deadlineAdded = false
    var checklistAdded = false

    var newTask : Task? = null


    fun createTask(){
        val task = newTask!!.copy()
        if(!descriptionAdded) task!!.description = null
        if(!placeAdded){
            task.lon = null
            task.lat = null
        }
        if(!personAdded) task.assignees = null
        if(!checklistAdded) task.checklist = null
        createTaskRepository.createTask(task, personAdded, checklistAdded)
    }

    fun getMembers(){
        members = createTaskRepository.getMembers(groupId)
    }

    fun assignMember(member : User){
        if(!newTask!!.assignees!!.contains(member.id)){
            newTask!!.assignees!!.add(member.id)
        }
    }

    fun excludeMember(member : User) : Boolean{
        return newTask!!.assignees!!.remove(member.id)
    }

    fun getSelectedMembers(): MutableList<Int> {
        return newTask!!.assignees!!
    }

    fun getChecklist(): MutableList<Check> {
        return newTask!!.checklist!!
    }

    fun addCheck(check: Check){
        newTask!!.checklist!!.add(check)
    }

    fun removeCheck(check : Check) : Boolean{
        return newTask!!.checklist!!.remove(check)
    }

    fun prepareTask(){
        newTask = Task(0,"",Importance.IMPORTANT,"",0.0,0.0,0.0, arrayListOf(), arrayListOf())
    }

    fun setDescription(words : String){
        newTask!!.description = words
    }

    fun setLatLng(latLng : LatLng){
        newTask!!.lat = latLng.latitude
        newTask!!.lon = latLng.longitude
    }

    fun setImportance(importance: Importance){
        newTask!!.importance = importance
    }

    protected fun finalize(){
        createTaskRepository.releaseStatus()
        createTaskRepository.releaseMember()
    }
}