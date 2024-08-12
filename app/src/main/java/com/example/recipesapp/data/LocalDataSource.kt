package com.example.recipesapp.data

import com.example.recipesapp.database.FavouriteRecipeEntity
import com.example.recipesapp.database.RecipeDao
import com.example.recipesapp.database.RecipeEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(private var recipesDao: RecipeDao)
   {


       fun getRecipes()=recipesDao.getRecipes()


       fun getFavouriteRecipes()=recipesDao.getAllFavouriteRecipes()

       suspend fun insertRecipes(recipeEntity: RecipeEntity)=recipesDao.insertRecipe(recipeEntity)

       suspend fun insertFavouriteRecipes(favouriteRecipeEntity: FavouriteRecipeEntity)
       {
           recipesDao.insertFavouriteRecipe(favouriteRecipeEntity)
       }

       suspend fun deleteFavouriteRecipe(favouriteRecipeEntity: FavouriteRecipeEntity)
       {
           recipesDao.deleteFavouriteRecipe(favouriteRecipeEntity)
       }

       suspend fun deleteAllFavouriteRecipes()
       {
           recipesDao.deleteAllFavouriteRecipes()
       }

}