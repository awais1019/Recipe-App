package com.example.recipesapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipes_table")
    fun getRecipes(): Flow<List<RecipeEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(recipe:FavouriteRecipeEntity)


    @Query("Select * from favourite_recipes_table order by Id Asc")
    fun getAllFavouriteRecipes():Flow<List<FavouriteRecipeEntity>>

    @Delete
    suspend fun deleteFavouriteRecipe(recipe: FavouriteRecipeEntity)

    @Query("Delete from favourite_recipes_table")
    suspend fun deleteAllFavouriteRecipes()



}