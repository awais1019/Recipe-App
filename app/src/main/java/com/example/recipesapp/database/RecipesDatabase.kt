package com.example.recipesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [RecipeEntity::class,FavouriteRecipeEntity::class],
    version = 1

)

@TypeConverters(RecipeTypeConvertor::class)
 abstract  class RecipesDatabase:RoomDatabase() {

    abstract fun getRecipeDao():RecipeDao


}