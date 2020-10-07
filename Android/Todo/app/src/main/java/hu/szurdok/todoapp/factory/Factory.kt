package hu.szurdok.todoapp.factory

interface Factory<T> {
    fun create() : T
}