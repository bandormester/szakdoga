package hu.szurdok.todoapp.data.group

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Group::class], version = 1)
abstract class GroupDatabase : RoomDatabase(){
    abstract fun groupDao(): GroupDao
}