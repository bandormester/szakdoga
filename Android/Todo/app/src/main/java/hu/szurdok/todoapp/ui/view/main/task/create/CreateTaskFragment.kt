package hu.szurdok.todoapp.ui.view.main.task.create

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.MarkerOptions
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.models.misc.Importance
import hu.szurdok.todoapp.data.models.Check
import hu.szurdok.todoapp.data.adapters.ChecklistAdapter
import hu.szurdok.todoapp.data.adapters.SelectMemberAdapter
import hu.szurdok.todoapp.data.models.User
import hu.szurdok.todoapp.ui.view.main.MainActivity
import hu.szurdok.todoapp.viewmodel.main.task.CreateTaskViewModel
import kotlinx.android.synthetic.main.fragment_create_task.*

class CreateTaskFragment : Fragment(), SelectMemberAdapter.MemberItemClickListener,
    ChecklistAdapter.CheckItemClickListener {

    private lateinit var createTaskViewModel : CreateTaskViewModel
    private var checkAdapter = ChecklistAdapter(true)
    private var border : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_create_task, container, false)
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
            handleImportanceSwitch(it as CardView)
        }

        cvCrucial.setOnClickListener {
            handleImportanceSwitch(it as CardView)
        }

        cvRegular.setOnClickListener {
            handleImportanceSwitch(it as CardView)
        }

        //Label
        etCreateTaskLabel.doOnTextChanged{text, _, _, _ ->
            createTaskViewModel.setLabel(text.toString())

        }

        //Description
        if(!createTaskViewModel.descriptionAdded) etCreateDescription.visibility = GONE
        setupDescriptionAdding()

        //Place
        if(!createTaskViewModel.placeAdded) llCreatePlace.visibility = GONE
        setupPlaceAdding()

        //Person
        if(!createTaskViewModel.personAdded) llCreatePerson.visibility = GONE
        setupPersonAdding()

        //Checklist
        if(!createTaskViewModel.checklistAdded) llCreateChecklist.visibility = GONE
        setupChecklistAdding()

        //CreateButton
        btFinalizeTask.setOnClickListener {
            createTaskViewModel.createTask()
        }
    }

    private fun setupDescriptionAdding(){
        setText(tvCreateDescription, createTaskViewModel.descriptionAdded, getString(R.string.description))
        tvCreateDescription.setOnClickListener {
            createTaskViewModel.descriptionAdded = showInputView(etCreateDescription, createTaskViewModel.descriptionAdded)
            setText(tvCreateDescription, createTaskViewModel.descriptionAdded, getString(R.string.description))
        }
        etCreateDescription.doOnTextChanged { text, _, _, _ ->
            createTaskViewModel.setDescription(text.toString())
        }
    }

    private fun setupPlaceAdding(){
        setText(tvCreatePlace, createTaskViewModel.placeAdded, getString(R.string.location))
        tvCreatePlace.setOnClickListener {
            createTaskViewModel.placeAdded = showInputView(llCreatePlace, createTaskViewModel.placeAdded)
            setText(tvCreatePlace, createTaskViewModel.placeAdded, getString(R.string.location))
        }
        btCreatePlace.setOnClickListener {
            val intent = Intent(activity, CreateMapActivity::class.java)
            startActivityForResult(intent, 420)
        }
    }

    private fun setupPersonAdding(){
        setText(tvCreatePerson, createTaskViewModel.personAdded, getString(R.string.assignee))
        tvCreatePerson.setOnClickListener {
            createTaskViewModel.personAdded = showInputView(llCreatePerson, createTaskViewModel.personAdded)
            setText(tvCreatePerson, createTaskViewModel.personAdded, getString(R.string.assignee))
            if(createTaskViewModel.personAdded) observeMembers()
        }
    }

    private fun setupChecklistAdding(){
        setText(tvCreateChecklist, createTaskViewModel.checklistAdded, getString(R.string.checklist))
        tvCreateChecklist.setOnClickListener {
            createTaskViewModel.checklistAdded = showInputView(llCreateChecklist, createTaskViewModel.checklistAdded)
            setText(tvCreateChecklist, createTaskViewModel.checklistAdded, getString(R.string.checklist))
            if(createTaskViewModel.checklistAdded) setupChecklistRecycler()
            Log.d("recview", createTaskViewModel.checklistAdded.toString())
        }
        btCreateChecklist.setOnClickListener {
            var check = Check(0,"Change here", false)
            checkAdapter.add(check)
            createTaskViewModel.addCheck(check)
        }
    }

    private fun handleImportanceSwitch(view : CardView){
        resetImportance()
        view.setContentPadding(border,border,border,border)
        when(view){
            cvCrucial -> createTaskViewModel.setImportance(Importance.CRUCIAL)
            cvRegular -> createTaskViewModel.setImportance(Importance.REGULAR)
            cvImportant -> createTaskViewModel.setImportance(Importance.IMPORTANT)
        }
    }

    private fun setupChecklistRecycler(){
        Log.d("recview","checklist lefut")
        checkAdapter.itemClickListener = this
        checkAdapter.addAll(createTaskViewModel.getChecklist())
        rvCreateChecklist.layoutManager = LinearLayoutManager(activity)
        rvCreateChecklist.adapter = checkAdapter
    }

    private fun observeMembers() {
        createTaskViewModel.getMembers()
        Log.d("recview", "setup person adding")
        createTaskViewModel.members.observe(viewLifecycleOwner){
            Log.d("recview", "members observed")
            setupPersonRecycler(it)
        }
    }

    private fun setupPersonRecycler(members : List<User>){
        val adapter = SelectMemberAdapter(createTaskViewModel, requireContext())
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

    private fun setText(tv : TextView, added : Boolean, detail : String){
        if(!added){
            tv.text = getString(R.string.add_detail, detail)
        }else{
            tv.text = getString(R.string.remove_detail, detail)
        }
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