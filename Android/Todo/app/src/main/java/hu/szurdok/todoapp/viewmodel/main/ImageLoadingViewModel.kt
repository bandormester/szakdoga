package hu.szurdok.todoapp.viewmodel.main

import android.content.Context
import android.widget.ImageView

interface ImageLoadingViewModel {
    fun getPicture(id : Int, imageView: ImageView, context: Context)
}