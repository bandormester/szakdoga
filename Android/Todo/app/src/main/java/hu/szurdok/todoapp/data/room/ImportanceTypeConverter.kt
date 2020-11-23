package hu.szurdok.todoapp.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import hu.szurdok.todoapp.data.models.misc.Importance
import hu.szurdok.todoapp.data.models.Check
import hu.szurdok.todoapp.data.models.User

class ImportanceTypeConverter {
    @TypeConverter
    fun fromImportance(value: Importance): Int {
        return value.ordinal
    }

    @TypeConverter
    fun intToImportance(value: Int): Importance {
        var importance = Importance.CRUCIAL
        when (value){
            1 -> importance = Importance.IMPORTANT
            2 -> importance = Importance.REGULAR
        }
        return importance
    }

    @TypeConverter
    fun listToJson(value: List<Int>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) : List<Int> {
        val array = Gson().fromJson(value, Array<Int>::class.java)?:emptyArray()
        return array.toList()
    }
    @TypeConverter
    fun checklistToJson(value: List<Check>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToChecklist(value: String) : List<Check> {
        val array = Gson().fromJson(value, Array<Check>::class.java)?:emptyArray()
        return array.toList()
    }

    @TypeConverter
    fun userToJson(value: List<User>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToUser(value: String) : List<User> {
        val array = Gson().fromJson(value, Array<User>::class.java)?:emptyArray()
        return array.toList()
    }
}