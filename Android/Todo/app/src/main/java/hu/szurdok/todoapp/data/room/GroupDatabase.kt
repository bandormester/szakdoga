package hu.szurdok.todoapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hu.szurdok.todoapp.data.models.Task
import hu.szurdok.todoapp.data.models.Group
import hu.szurdok.todoapp.data.models.TaskCard

@Database(entities = [Group::class, Task::class, TaskCard::class], version = 1)
@TypeConverters(ImportanceTypeConverter::class)
abstract class GroupDatabase : RoomDatabase(){
    abstract fun groupDao(): GroupDao
    abstract fun taskDao(): TaskDao
    abstract fun taskCardDao(): TaskCardDao
}