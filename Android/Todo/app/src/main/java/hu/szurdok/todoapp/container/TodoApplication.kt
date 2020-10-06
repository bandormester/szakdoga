package hu.szurdok.todoapp.container

import android.app.Application

class TodoApplication : Application() {
    val appContainer = AppContainer()
}