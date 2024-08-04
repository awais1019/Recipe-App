package com.example.recipesapp.database

import androidx.room.TypeConverter
import com.example.recipesapp.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeTypeConvertor
{
     var gson=Gson()


    @TypeConverter
    fun recipesToString(recipe: FoodRecipe):String
    {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToRecipes(data:String):FoodRecipe
    {
        val listType=object: TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data,listType)

    }

}