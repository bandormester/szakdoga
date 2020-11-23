package hu.szurdok.todoapp.container

import hu.szurdok.todoapp.TodoApplication

class AppContainer (val app : TodoApplication){
    private var loginContainer : LoginContainer? = null

    private var mainContainer : MainContainer? = null

    fun getMainContainer() : MainContainer{
        mainContainer = MainContainer(app)
        return mainContainer!!
    }

    fun releaseMainContainer(){
        mainContainer = null
    }

    fun getLoginContainer() : LoginContainer{
        loginContainer = LoginContainer(app)
        return loginContainer!!
    }

    fun releaseLoginContainer(){
        loginContainer = null
    }
}