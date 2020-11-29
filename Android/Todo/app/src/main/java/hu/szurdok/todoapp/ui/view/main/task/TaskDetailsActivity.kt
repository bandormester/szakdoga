package hu.szurdok.todoapp.ui.view.main.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.TodoApplication
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.data.models.Check
import hu.szurdok.todoapp.data.adapters.ChecklistAdapter
import hu.szurdok.todoapp.data.models.TaskCard
import hu.szurdok.todoapp.data.adapters.GroupMemberAdapter
import hu.szurdok.todoapp.data.models.User
import hu.szurdok.todoapp.viewmodel.main.task.TaskDetailsViewModel
import kotlinx.android.synthetic.main.activity_task_details.*

class TaskDetailsActivity : AppCompatActivity(), OnMapReadyCallback,
    ChecklistAdapter.CheckItemClickListener, GroupMemberAdapter.MemberItemClickListener {

    private lateinit var taskDetailsViewModel : TaskDetailsViewModel
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        val taskCard = intent.getSerializableExtra("taskCard") as TaskCard
        val apiToken = intent.getSerializableExtra("api-token") as ApiToken

        showViews(taskCard)

        taskDetailsViewModel = (application as TodoApplication).appContainer.getMainContainer()
            .taskDetailsRepositoryFactory.create(apiToken, taskCard.id)

        taskDetailsViewModel.task.observe(this){ task ->
            if(task!=null) {
                task.lat?.let {
                    task.lon?.let { it1 -> moveMap(it, it1) }
                }

                tvLabel.text = task.label

                if (!task.description.isNullOrEmpty()) {
                    tvDescription.text = task.description
                }

                if (!task.assignees.isNullOrEmpty()) {
                    Log.d("assignees", "lefut")
                    setupAssigneeRecycler(task.assignees!!)
                }

                if (!task.checklist.isNullOrEmpty()) {
                    Log.d("checks", "lefut")
                    setupCheckRecycler(task.checklist!!)
                }
            }
        }
    }

    private fun showViews(taskCard: TaskCard) {
        if(taskCard.hasPlace)
            inflateMap()
        if(taskCard.hasDescription)
            tvDescription.visibility = VISIBLE
        if(taskCard.hasChecklist)
            rvChecklist.visibility = VISIBLE
        if(taskCard.hasAssignees)
            rvAssignees.visibility = VISIBLE
    }

    private fun setupAssigneeRecycler(assignees : List<User>){
        val adapter = GroupMemberAdapter(this)
        adapter.removeAble = false
        adapter.itemClickListener = this
        adapter.addAll(assignees)
        rvAssignees.layoutManager = LinearLayoutManager(this)
        rvAssignees.adapter = adapter
    }

    private fun setupCheckRecycler(checks : List<Check>){
        val checkAdapter = ChecklistAdapter(false)
        checkAdapter.itemClickListener = this
        checkAdapter.addAll(checks)
        rvChecklist.layoutManager = LinearLayoutManager(this)
        rvChecklist.adapter = checkAdapter
    }

    private fun inflateMap(){
        val mapView = layoutInflater.inflate(R.layout.map_inflate, llInflateHolder, false)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.detailsMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
        llInflateHolder.addView(mapView, 0)
    }

    private fun moveMap(lat : Double, lng : Double){
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, lng)))
        val markerOptions = MarkerOptions().title("Szurdok").position(LatLng(lat, lng))
        mMap?.addMarker(markerOptions)
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.uiSettings.isZoomControlsEnabled = true
        mMap = p0
    }

    override fun onTextEdited(check: Check) {
        Log.d("adapter","not editable")
    }

    override fun onCheckChecked(check: Check) {
        taskDetailsViewModel.checkItem(check)
    }

    override fun onMemberSelected(member: User) {
        Toast.makeText(this, member.fullName, Toast.LENGTH_SHORT).show()
    }

    override fun onMemberRemoved(member: User) {
        Log.d("recycler", "removeclicked")
    }


}