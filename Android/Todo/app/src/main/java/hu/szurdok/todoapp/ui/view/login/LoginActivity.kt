package hu.szurdok.todoapp.ui.view.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.TodoApplication
import hu.szurdok.todoapp.container.LoginContainer

class LoginActivity : AppCompatActivity() {

    lateinit var loginContainer : LoginContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LoginFragment())
                    .commitNow()
        }

        loginContainer = (application as TodoApplication).appContainer.getLoginContainer()
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as TodoApplication).appContainer.releaseLoginContainer()
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