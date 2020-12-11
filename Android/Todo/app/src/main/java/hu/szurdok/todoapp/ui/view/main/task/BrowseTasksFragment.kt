package hu.szurdok.todoapp.ui.view.main.task

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.adapters.BrowseTasksAdapter
import hu.szurdok.todoapp.data.models.TaskCard
import hu.szurdok.todoapp.ui.view.main.MainActivity
import hu.szurdok.todoapp.viewmodel.main.task.BrowseTasksViewModel
import kotlinx.android.synthetic.main.fragment_browse_tasks.*

class BrowseTasksFragment : Fragment(), BrowseTasksAdapter.TaskItemClickListener {

        private lateinit var browseTasksViewModel: BrowseTasksViewModel

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            return inflater.inflate(R.layout.fragment_browse_tasks, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            btAllTasks.setOnClickListener {
                switchButton(it as Button)
                browseTasksViewModel.getTasks()
            }

            btOwnedTasks.setOnClickListener {
                switchButton(it as Button)
                browseTasksViewModel.getDoneTasks()
            }

            btMineTasks.setOnClickListener {
                switchButton(it as Button)
                browseTasksViewModel.getMyTasks()
            }

            browseTasksViewModel =
                (activity as MainActivity).mainContainer.browseTasksViewModelFactory.create((activity as MainActivity).token, (activity as MainActivity).groupId)

            browseTasksViewModel.tasks.observe(viewLifecycleOwner) {
                setupRecyclerView(it)
            }
        }

        private fun setupRecyclerView(list: List<TaskCard>) {
            val tasksAdapter = BrowseTasksAdapter(activity as Activity)
            tasksAdapter.itemClickListener = this
            tasksAdapter.addAll(list)

            val gridManager = GridLayoutManager(activity, 2)
            gridManager.spanSizeLookup = object :
                GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return tasksAdapter.getSpan(position)
                }
            }

            rvBrowseTasks.layoutManager = gridManager
            rvBrowseTasks.adapter = tasksAdapter
        }

    private fun switchButton(button : Button){
        btMineTasks.background = ResourcesCompat.getDrawable(resources, R.drawable.button_outlined_blue_rounded, null)
        btOwnedTasks.background = ResourcesCompat.getDrawable(resources, R.drawable.button_outlined_blue_rounded,null)
        btAllTasks.background = ResourcesCompat.getDrawable(resources, R.drawable.button_outlined_blue_rounded,null)
        
        btMineTasks.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        btOwnedTasks.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        btAllTasks.setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        
        button.background = ResourcesCompat.getDrawable(resources, R.drawable.button_outlined_white_rounded, null)
        button.setTextColor(ResourcesCompat.getColor(resources, R.color.light_blue, null))
    }

    override fun onTaskSelected(task: TaskCard) {
        val intent = Intent(activity, TaskDetailsActivity::class.java).apply {
            putExtra("taskCard", task)
        }
        startActivity(intent)
    }
}