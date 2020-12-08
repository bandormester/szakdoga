package hu.szurdok.todoapp.viewmodel.main.task

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.misc.Importance
import hu.szurdok.todoapp.data.models.misc.RegistrationStatus
import hu.szurdok.todoapp.data.models.Check
import hu.szurdok.todoapp.data.repository.main.CreateTaskRepository
import hu.szurdok.todoapp.data.models.Task
import hu.szurdok.todoapp.data.models.User
import hu.szurdok.todoapp.viewmodel.main.ImageLoadingViewModel

class CreateTaskViewModel(
    private val createTaskRepository: CreateTaskRepository,
    private val token : ApiToken,
    private val groupId : Int
) : ViewModel(), ImageLoadingViewModel {
    val creationStatus : LiveData<RegistrationStatus> = createTaskRepository.subscribeStatus()
    lateinit var members : LiveData<List<User>>

    var descriptionAdded = false
    var placeAdded = false
    var personAdded = false
    var checklistAdded = false

    var newTask : Task? = null


    fun createTask(){
        val task = newTask!!.copy()
        if(!descriptionAdded) task.description = null
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
        if(!newTask!!.assignees!!.contains(member)){
            newTask!!.assignees!!.add(member)
        }
    }

    fun excludeMember(member : User) : Boolean{
        return newTask!!.assignees!!.remove(member)
    }

    fun getSelectedMembers(): MutableList<User> {
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
        newTask = Task(0,token.id, groupId,"",
            Importance.IMPORTANT,"",0.0,0.0, arrayListOf(), arrayListOf())
    }

    fun setDescription(words : String){
        newTask!!.description = words
    }

    fun setLatLng(latLng : LatLng){
        newTask!!.lat = latLng.latitude
        newTask!!.lon = latLng.longitude
    }

    fun setLabel(words : String){
        newTask!!.label = words
    }

    fun setImportance(importance: Importance){
        newTask!!.importance = importance
    }

    override fun getPicture(id: Int, imageView: ImageView, context: Context) {
        createTaskRepository.getPicture(id, imageView, context)
    }

    protected fun finalize(){
        createTaskRepository.releaseStatus()
        createTaskRepository.releaseMember()
    }
}