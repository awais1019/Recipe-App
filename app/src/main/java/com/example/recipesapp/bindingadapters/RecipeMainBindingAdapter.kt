package com.example.recipesapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.recipesapp.database.RecipeEntity
import com.example.recipesapp.models.FoodRecipe
import com.example.recipesapp.util.NetworkResult

class RecipeMainBindingAdapter {

    companion object {

        @BindingAdapter("readDatabase", "readApiResponse", requireAll = true)
        @JvmStatic
        fun setImageViewVisibility(imageView: ImageView, database: List<RecipeEntity>?, apiResponse: NetworkResult<FoodRecipe>) {
            when (apiResponse) {
                is NetworkResult.Error -> {

                    imageView.visibility = if (database.isNullOrEmpty()) View.VISIBLE else View.GONE
                }
                is NetworkResult.Loading -> {
                    imageView.visibility = View.GONE
                }
                is NetworkResult.Success -> {
                    imageView.visibility = View.GONE
                }
            }
        }

        @BindingAdapter("readDatabase1", "readApiResponse1", requireAll = true)
        @JvmStatic
        fun setTextViewVisibility(textView: TextView, apiResponse: NetworkResult<FoodRecipe>, database: List<RecipeEntity>?) {
            when (apiResponse) {
                is NetworkResult.Error -> {
                    // Show TextView with error message only if database is empty
                    textView.visibility = if (database.isNullOrEmpty()) View.VISIBLE else View.GONE
                    textView.text = apiResponse.msg.toString()
                }
                is NetworkResult.Loading -> {
                    textView.visibility = View.GONE
                }
                is NetworkResult.Success -> {
                    textView.visibility = View.GONE
                }
            }
        }
    }
}
