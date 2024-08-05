package com.example.recipesapp.viewmodels

import androidx.compose.ui.window.Dialog
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.data.DataStoreRepository
import com.example.recipesapp.data.MealAndDietType
import com.example.recipesapp.util.Constants.Companion.API_KEY
import com.example.recipesapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.recipesapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.recipesapp.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.recipesapp.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.recipesapp.util.Constants.Companion.QUERY_API_KEY
import com.example.recipesapp.util.Constants.Companion.QUERY_DIET
import com.example.recipesapp.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.recipesapp.util.Constants.Companion.QUERY_NUMBER
import com.example.recipesapp.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor (private var datastore: DataStoreRepository): ViewModel() {

    private var mealType= DEFAULT_MEAL_TYPE
    private var dietType= DEFAULT_DIET_TYPE

    val readMealAndDiet: Flow<MealAndDietType> = datastore.readMealAndDietType

    fun saveMealAndDiet(mealType:String,mealTypeId:Int,dietType:String,dietTypeId:Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            datastore.saveMealAndDietType(mealType,mealTypeId,dietType,dietTypeId)
        }
    }

     fun getQueries(): HashMap<String, String> {

         viewModelScope.launch {
             readMealAndDiet.collect()
             {
                 mealType=it.selectedMealType
                 dietType=it.selectedDietType
             }
         }
        return hashMapOf(
            QUERY_NUMBER to DEFAULT_RECIPES_NUMBER,
            QUERY_API_KEY to API_KEY,
            QUERY_TYPE to mealType,
            QUERY_DIET to dietType,
            QUERY_ADD_RECIPE_INFORMATION to "true",
            QUERY_FILL_INGREDIENTS to "true"
        )
    }

}


