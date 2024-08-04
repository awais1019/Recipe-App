package com.example.recipesapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.models.FoodRecipe
import com.example.recipesapp.util.Constants.Companion.RECIPES_TABLE


@Entity(tableName = RECIPES_TABLE)
class RecipeEntity(var foodRecipe:FoodRecipe) {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0

}