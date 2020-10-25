package hu.szurdok.todoapp.ui.view.main.create

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.MarkerOptions
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.Importance
import hu.szurdok.todoapp.data.check.Check
import hu.szurdok.todoapp.data.check.ChecklistAdapter
import hu.szurdok.todoapp.data.user.SelectMemberAdapter
import hu.szurdok.todoapp.data.user.User
import hu.szurdok.todoapp.ui.view.main.MainActivity
import hu.szurdok.todoapp.viewmodel.main.CreateTaskViewModel
import kotlinx.android.synthetic.main.activity_create_person.*
import kotlinx.android.synthetic.main.create_task_fragment.*

class CreateTaskFragment : Fragment(), SelectMemberAdapter.MemberItemClickListener,
    ChecklistAdapter.CheckItemClickListener {

    private lateinit var createTaskViewModel : CreateTaskViewModel
    private var checkAdapter = ChecklistAdapter(true)
    private var border : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.create_task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        border = (resources.displayMetrics.density * 3 + 0.5f).toInt()

        createTaskViewModel = (activity as MainActivity).mainContainer.createTaskViewModelFactory
            .create((activity as MainActivity).token, (activity as MainActivity).groupId)

        createTaskViewModel.creationStatus.observe(viewLifecycleOwner){
            if(it.successful){
               (activity as MainActivity).toTasksFragment()
            }
            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
        }

        createTaskViewModel.prepareTask()

        cvImportant.setOnClickListener {
            resetImportance()
            cvImportant.setContentPadding(border,border,border,border)
            createTaskViewModel.setImportance(Importance.IMPORTANT)
        }

        cvCrucial.setOnClickListener {
            resetImportance()
            cvCrucial.setContentPadding(border,border,border,border)
            createTaskViewModel.setImportance(Importance.CRUCIAL)
        }

        cvRegular.setOnClickListener {
            resetImportance()
            cvRegular.setContentPadding(border,border,border,border)
            createTaskViewModel.setImportance(Importance.REGULAR)
        }

        //Description
        if(!createTaskViewModel.descriptionAdded) etCreateDescription.visibility = GONE
        tvCreateDescription.setOnClickListener {
            createTaskViewModel.descriptionAdded = showInputView(etCreateDescription, createTaskViewModel.descriptionAdded)
        }
        etCreateDescription.doOnTextChanged { text, _, _, _ ->
            createTaskViewModel.setDescription(text.toString())
        }

        //Place
        if(!createTaskViewModel.placeAdded) llCreatePlace.visibility = GONE
        tvCreatePlace.setOnClickListener {
            createTaskViewModel.placeAdded = showInputView(llCreatePlace, createTaskViewModel.placeAdded)
        }
        btCreatePlace.setOnClickListener {
            val intent = Intent(activity, CreateMapActivity::class.java)
            startActivityForResult(intent, 420)
        }

        //Person
        if(!createTaskViewModel.personAdded) llCreatePerson.visibility = GONE
        tvCreatePerson.setOnClickListener {
            createTaskViewModel.personAdded = showInputView(llCreatePerson, createTaskViewModel.personAdded)
            if(createTaskViewModel.personAdded) setupPersonAdding()
        }

        //Deadline TODO
        tvCreateTime.setOnClickListener {
            createTaskViewModel.deadlineAdded = showInputView(llCreateTime, createTaskViewModel.deadlineAdded)
        }

        //Checklist
        if(!createTaskViewModel.checklistAdded) llCreateChecklist.visibility = GONE
        tvCreateChecklist.setOnClickListener {
            createTaskViewModel.checklistAdded = showInputView(llCreateChecklist, createTaskViewModel.checklistAdded)
            if(createTaskViewModel.checklistAdded) setupChecklistAdding()
            Log.d("recview", createTaskViewModel.checklistAdded.toString())
        }
        btCreateChecklist.setOnClickListener {
            var check = Check("Change here", false)
            checkAdapter.add(check)
            createTaskViewModel.addCheck(check)
        }

        //CreateButton
        btFinalizeTask.setOnClickListener {
            createTaskViewModel.createTask()
        }
    }

    private fun setupChecklistAdding(){
        Log.d("recview","checklist lefut")
        checkAdapter.itemClickListener = this
        checkAdapter.addAll(createTaskViewModel.getChecklist())
        rvCreateChecklist.layoutManager = LinearLayoutManager(activity)
        rvCreateChecklist.adapter = checkAdapter
    }

    private fun setupPersonAdding() {
        createTaskViewModel.getMembers()
        Log.d("recview", "setup person adding")
        createTaskViewModel.members.observe(viewLifecycleOwner){
            Log.d("recview", "members observed")
            setupPersonAddingRecycler(it)
        }
    }

    private fun setupPersonAddingRecycler(members : List<User>){
        val adapter = SelectMemberAdapter()
        adapter.itemClickListener = this
        adapter.addAll(members, createTaskViewModel.getSelectedMembers())
        lvCreatePerson.layoutManager = LinearLayoutManager(activity)
        lvCreatePerson.adapter = adapter
    }

    private fun showInputView(view : View, visible : Boolean) : Boolean{
        if(visible){
            view.visibility = GONE
        }else{
            view.visibility = VISIBLE
        }

        return !visible
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 420){
            if(resultCode == RESULT_OK){
                val result = data!!.getParcelableExtra<MarkerOptions>("result")
                tvCreatePlaceName.text = result!!.title
                createTaskViewModel.setLatLng(result.position)
            }
        }
    }

    override fun onMemberSelected(member: User) {
        createTaskViewModel.assignMember(member)
    }

    override fun onMemberExcluded(member: User) {
        createTaskViewModel.excludeMember(member)
    }

    override fun onTextEdited(check: Check) {
       // createTaskViewModel.editCheck(check)
    }

    override fun onCheckChecked(check: Check) {
        checkAdapter.remove(check)
        createTaskViewModel.removeCheck(check)
    }

    private fun resetImportance(){
        cvImportant.setContentPadding(0,0,0,0)
        cvCrucial.setContentPadding(0,0,0,0)
        cvRegular.setContentPadding(0,0,0,0)
    }

}