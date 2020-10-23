package hu.szurdok.todoapp.viewmodel.main

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.szurdok.todoapp.data.ApiToken
import hu.szurdok.todoapp.data.group.Group
import hu.szurdok.todoapp.data.repository.main.GroupDetailsRepository
import hu.szurdok.todoapp.data.user.User

class GroupDetailsViewModel(
    private val groupDetailsRepository: GroupDetailsRepository,
    private val token: ApiToken,
    private val groupId : Int,
    private val context : Context
) : ViewModel() {
    val group : LiveData<Group> = groupDetailsRepository.getGroup(groupId)

    val members : MutableLiveData<List<User>> = groupDetailsRepository.getMembers(groupId)

    val removeMessage : MutableLiveData<String> = groupDetailsRepository.getRemoveMessage()

    fun removeMember(groupId: Int, token : ApiToken, removedId : Int){
        groupDetailsRepository.removeMember(groupId, token, removedId)
    }

    fun copyInviteCode(){
        val cmm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", group.value?.joinCode?:"")
        cmm.setPrimaryClip(clip)
    }

    protected fun finalize(){
        groupDetailsRepository.releaseRemoveMessage()
    }
}