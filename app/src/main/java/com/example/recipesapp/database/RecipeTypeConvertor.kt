package com.example.recipesapp.database

import androidx.room.TypeConverter
import com.example.recipesapp.models.FoodRecipe
import com.example.recipesapp.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeTypeConvertor
{
     private var gson=Gson()


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
    @TypeConverter
    fun resultToString(result: Result):String
    {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToResult(data:String):Result
    {
        val listType=object: TypeToken<Result>() {}.type
        return gson.fromJson(data,listType)

    }

}