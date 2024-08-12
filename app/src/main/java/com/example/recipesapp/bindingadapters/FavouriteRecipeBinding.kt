package com.example.recipesapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.adapters.FavouriteRecipesAdapter
import com.example.recipesapp.database.FavouriteRecipeEntity

class FavouriteRecipeBinding {


    companion object {

        @BindingAdapter("setDataAndVisibility", "adapter", requireAll = false)
        @JvmStatic
        fun setDataAndVisibility(view: View, recipe: List<FavouriteRecipeEntity>?, adapter: FavouriteRecipesAdapter?) {
            if (recipe.isNullOrEmpty()) {
                when (view) {
                    is TextView, is ImageView -> view.visibility = View.VISIBLE
                    is RecyclerView -> view.visibility = View.GONE
                }
            } else {
                when (view) {
                    is TextView, is ImageView -> view.visibility = View.GONE
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        adapter?.updateList(recipe)
                    }
                }
            }
        }
    }

}