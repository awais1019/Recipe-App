package com.example.recipesapp.util

class Constants {

    companion object
    {
        const val API_KEY="9a0551c4b5744b549ce397b13a044161"
        const val BASE_URL="https://api.spoonacular.com"

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

    }
}