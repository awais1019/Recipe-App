package com.example.recipesapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.recipesapp.database.RecipeEntity
import com.example.recipesapp.models.FoodRecipe
import com.example.recipesapp.util.NetworkResult

class RecipeMainBindingAdapter {

    companion object{

        @BindingAdapter("readDatabase","readApiResponse", requireAll = true)
        @JvmStatic
        fun errorImgView(imageView: ImageView,database:List<RecipeEntity>?,apiResponse: NetworkResult<FoodRecipe>)
        {
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty())
            {
                imageView.visibility= View.VISIBLE
            }
            else if(apiResponse is NetworkResult.Loading)
            {
                imageView.visibility= View.GONE
            }
            else if(apiResponse is NetworkResult.Success)
            {
                imageView.visibility= View.GONE
            }
        }
        @BindingAdapter("readDatabase1","readApiResponse1", requireAll = true)
        @JvmStatic
        fun errorTextView(textView: TextView, apiResponse: NetworkResult<FoodRecipe>,database: List<RecipeEntity>?)
        {
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty())
            {
                textView.visibility= View.VISIBLE
                textView.text=apiResponse.msg.toString()
            }
            else if(apiResponse is NetworkResult.Loading)
            {
                textView.visibility=View.GONE
            }
            else if(apiResponse is NetworkResult.Success)
            {
                textView.visibility=View.GONE
            }
        }

    }

}