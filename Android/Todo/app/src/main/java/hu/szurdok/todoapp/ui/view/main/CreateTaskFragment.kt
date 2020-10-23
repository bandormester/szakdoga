package hu.szurdok.todoapp.ui.view.main

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.viewmodel.main.CreateTaskViewModel
import kotlinx.android.synthetic.main.create_task_fragment.*
import kotlinx.android.synthetic.main.group_details_fragment.*

class CreateTaskFragment : Fragment() {

    private lateinit var createTaskViewModel : CreateTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.create_task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createTaskViewModel = (activity as MainActivity).mainContainer.createTaskViewModelFactory
            .create((activity as MainActivity).token, (activity as MainActivity).groupId)

        createTaskViewModel.creationStatus.observe(viewLifecycleOwner){
            if(it.successful){
               (activity as MainActivity).toTasksFragment()
            }
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
        }

        createTaskViewModel.prepareTask()

        //Description
        tvCreateDescription.setOnClickListener {
            createTaskViewModel.descriptionAdded = showInputView(etCreateDescription, createTaskViewModel.descriptionAdded)
        }
        etCreateDescription.doOnTextChanged { text, _, _, _ ->
            createTaskViewModel.setDescription(text as String)
        }

        //Place
        tvCreatePlace.setOnClickListener {
            createTaskViewModel.placeAdded = showInputView(llCreatePlace, createTaskViewModel.placeAdded)
        }
        btCreatePlace.setOnClickListener {
            
        }

        tvCreatePlace.setOnClickListener {
            createTaskViewModel.personAdded = showInputView(llCreatePerson, createTaskViewModel.personAdded)
        }

        tvCreatePlace.setOnClickListener {
            createTaskViewModel.deadlineAdded = showInputView(llCreateTime, createTaskViewModel.deadlineAdded)
        }

        tvCreatePlace.setOnClickListener {
            createTaskViewModel.checklistAdded = showInputView(llCreateChecklist, createTaskViewModel.checklistAdded)
        }

    }

    private fun showInputView(view : View, visible : Boolean) : Boolean{
        if(visible){
            view.visibility = GONE
        }else{
            view.visibility = VISIBLE
        }

        return !visible
    }

}