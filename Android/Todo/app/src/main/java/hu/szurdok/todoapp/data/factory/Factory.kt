package hu.szurdok.todoapp.data.factory

interface Factory<T> {
    fun create() : T
}