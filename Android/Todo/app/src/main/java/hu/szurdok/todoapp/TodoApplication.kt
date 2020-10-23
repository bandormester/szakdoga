package hu.szurdok.todoapp

import android.app.Application
import hu.szurdok.todoapp.container.AppContainer

class TodoApplication : Application() {
    val appContainer = AppContainer(this)

    val SHARED_PREFENCES = "todoAppPreferences"
    val TOKEN_KEY = "user_name"
    val USERID_KEY = "user_id"
}