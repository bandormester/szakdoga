package hu.szurdok.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.szurdok.todoapp.container.AppContainer
import hu.szurdok.todoapp.container.TodoApplication
import hu.szurdok.todoapp.ui.main.LoginFragment
import hu.szurdok.todoapp.ui.main.RegisterFragment
import hu.szurdok.todoapp.ui.main.UserProfileFragment

class MainActivity : AppCompatActivity() {

    lateinit var appContainer : AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment())
                    .commitNow()
        }

        appContainer = (application as TodoApplication).appContainer
        appContainer.setupContainer(baseContext)
    }

    fun toRegisterFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, RegisterFragment()).commit()
    }

    fun backToLogin(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoginFragment()).commit()
    }
}