package hu.szurdok.todoapp.viewmodel.main.group

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.Group
import hu.szurdok.todoapp.data.repository.main.ChooseGroupRepository
import java.io.ByteArrayOutputStream

class ChooseGroupViewModel(
    private val chooseGroupRepository: ChooseGroupRepository,
    private val token : ApiToken
) : ViewModel() {
    val groups : LiveData<List<Group>> = chooseGroupRepository.getGroups(token)

    val createStatus : LiveData<String> = chooseGroupRepository.getStatus()
    
    fun createGroup(bitmap : Bitmap, name : String, description : String, joinCode : String, hasPicture : Boolean){
        var picture : ByteArray? = null

        if(hasPicture){
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            picture = stream.toByteArray()
        }

        chooseGroupRepository.createGroup(picture, token, name, description, joinCode, hasPicture)
    }

    fun joinGroup(code : String){
        chooseGroupRepository.joinGroup(code, token.id)
    }

    fun getGroupPicture(id: Int, imageView: ImageView, context: Context) {
        chooseGroupRepository.getGroupPicture(id, imageView, context)
    }

    protected fun finalize(){
        chooseGroupRepository.releaseStatus()
    }
}