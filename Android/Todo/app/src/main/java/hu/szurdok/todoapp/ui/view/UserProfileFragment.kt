package hu.szurdok.todoapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.viewmodel.UserProfileViewModel

class UserProfileFragment : Fragment() {
    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_activity, container, false)
    }

 //  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 //      super.onViewCreated(view, savedInstanceState)

 //      (activity as MainActivity).appContainer.userContainer = UserContainer((activity as MainActivity).appContainer.userRepository)
 //      viewModel = (activity as MainActivity).appContainer.userContainer!!.userViewModelFactory.create()

 //      viewModel.user.observe(viewLifecycleOwner){
 //          if(it.isNotEmpty()){
 //              tvFullName.text = it[0].fullName
 //          }
 //      }
 //  }

 //  override fun onDestroy() {
 //      (activity as MainActivity).appContainer.userContainer = null
 //      super.onDestroy()
 //  }
}