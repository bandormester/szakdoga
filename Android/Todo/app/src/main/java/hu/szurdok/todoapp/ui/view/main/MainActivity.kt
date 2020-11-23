package hu.szurdok.todoapp.ui.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.TodoApplication
import hu.szurdok.todoapp.container.MainContainer
import hu.szurdok.todoapp.data.models.misc.ApiToken
import hu.szurdok.todoapp.ui.view.main.task.create.CreateTaskFragment
import hu.szurdok.todoapp.ui.view.main.group.ChooseGroupFragment
import hu.szurdok.todoapp.ui.view.main.group.GroupDetailsFragment
import hu.szurdok.todoapp.ui.view.main.task.BrowseTasksFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    internal lateinit var mainContainer : MainContainer
    internal lateinit var token : ApiToken
    internal var groupId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ChooseGroupFragment())
                    .commitNow()
        }
        mainContainer = (application as TodoApplication).appContainer.getMainContainer()

        token = intent.getSerializableExtra("api-token") as ApiToken

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.create_nav_button -> toCreateFragment()
                R.id.tasks_nav_button -> toTasksFragment()
                R.id.group_nav_button -> toGroupFragment()
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as TodoApplication).appContainer.releaseMainContainer()
    }

    private fun toCreateFragment(): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, CreateTaskFragment())
            .commitNow()
        return true
    }

    internal fun toTasksFragment(): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, BrowseTasksFragment())
            .commitNow()
        return true
    }

    private fun toGroupFragment(): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, GroupDetailsFragment())
            .commitNow()
        return true
    }


    internal fun groupSelected(id : Int){
        groupId = id
        bottomNavigationView.visibility = View.VISIBLE
        toGroupFragment()
    }
}