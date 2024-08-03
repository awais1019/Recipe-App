package com.example.recipesapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.recipesapp.util.Constants.Companion.API_KEY
import com.example.recipesapp.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.recipesapp.util.Constants.Companion.QUERY_API_KEY
import com.example.recipesapp.util.Constants.Companion.QUERY_DIET
import com.example.recipesapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.recipesapp.util.Constants.Companion.QUERY_NUMBER
import com.example.recipesapp.util.Constants.Companion.QUERY_TYPE

class RecipeViewModel: ViewModel() {


     fun getQueries(): HashMap<String, String> {

        return hashMapOf(
            QUERY_NUMBER to "50",
            QUERY_API_KEY to API_KEY,
            QUERY_TYPE to "snack",
            QUERY_DIET to "vegan",
            QUERY_ADD_RECIPE_INFORMATION to "true",
            QUERY_FILL_INGREDIENTS to "true"
        )
    }

}


