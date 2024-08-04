package com.example.recipesapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.recipesapp.R

class RecipeRowBindingAdapter {

    companion object
    {

        @BindingAdapter("loadImgFromSource")
        @JvmStatic
        fun loadImgFromURL(imgView: ImageView,source:String)
        {
            imgView.load(source)
            {
                crossfade(600)
                error(R.drawable.error_placeholder)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic
        fun setNumberOfLikes(textView: TextView, likes: Int)
        {
            textView.text=likes.toString()
        }
        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setCookingTime(textView: TextView, time: Int)
        {
            textView.text=time.toString()
        }

        @BindingAdapter("setVegan")
        @JvmStatic
        fun setVegan(view: View,vegan:Boolean)
        {
            if(vegan)
                when(view)
                {
                    is TextView->
                    {
                        view.setTextColor(view.context.getColor(R.color.green))
                    }
                    is ImageView ->
                    {
                        view.setColorFilter(view.context.getColor(R.color.green))
                    }
                }
        }
    }
}