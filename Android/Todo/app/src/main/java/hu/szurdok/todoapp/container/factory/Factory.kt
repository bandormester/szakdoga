package hu.szurdok.todoapp.container.factory

interface Factory<T> {
    fun create() : T
}