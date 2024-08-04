package com.example.recipesapp.data

import androidx.room.Dao
import androidx.room.Query
import com.example.recipesapp.database.RecipeDao
import com.example.recipesapp.database.RecipeEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private var recipesDao: RecipeDao)
   {


       fun getRecipes()=recipesDao.getRecipes()


       suspend fun insertRecipes(recipeEntity: RecipeEntity)=recipesDao.insertRecipe(recipeEntity)

}