package com.example.recipesapp.util

import androidx.datastore.preferences.core.Preferences

class Constants {

    companion object
    {

        //Replace your api key
        const val API_KEY="Your API Key"
        const val BASE_URL="https://api.spoonacular.com"

        const val BASE_IMG_URL="https://img.spoonacular.com/ingredients_100x100/"

        //Api Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"


        //Room Database
        const val DATABASE_NAME="recipes_database"
        const val RECIPES_TABLE="recipes_table"
        
        
        
        
        
        //Bottom Sheet Values
        
        const val DEFAULT_MEAL_TYPE="main course"
        const val DEFAULT_DIET_TYPE="gluten free"
        const val DEFAULT_RECIPES_NUMBER="50"


        //Preference values
        const val PREFERENCES_NAME="food_preferences"
        const val PREFERENCE_MEAL_TYPE="mealType"
        const val PREFERENCE_MEAL_TYPE_ID="mealTypeId"
        const val PREFERENCE_DIET_TYPE="dietType"
        const val PREFERENCE_DIET_TYPE_ID="dietTypeId"

        const val BACK_ONLINE="backOnline"

    }
}