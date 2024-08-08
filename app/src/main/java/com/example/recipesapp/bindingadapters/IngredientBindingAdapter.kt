package com.example.recipesapp.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.text.capitalize
import androidx.databinding.BindingAdapter
import coil.load
import com.example.recipesapp.R
import com.example.recipesapp.util.Constants.Companion.BASE_IMG_URL
import java.util.Locale

class IngredientBindingAdapter {


    companion object
    {
        @BindingAdapter("loadImage")
        @JvmStatic
        fun loadImg(imgView:ImageView,img:String)
        {
            imgView.load(BASE_IMG_URL + img)
            {
                crossfade(600)
                error(R.drawable.ic_error)
            }
        }

        @BindingAdapter("setAmount")
        @JvmStatic
        fun loadAmount(textView: TextView, amount:Double)
        {
            textView.text=amount.toString()
        }

        @BindingAdapter("setTitle")
        @JvmStatic
        fun loadAmount(textView: TextView, name:String)
        {
            textView.text =name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
            }
        }

    }
}