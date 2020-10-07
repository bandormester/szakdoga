package hu.szurdok.todoapp

import android.app.Application
import hu.szurdok.todoapp.container.AppContainer

class TodoApplication : Application() {
    val appContainer = AppContainer()
}