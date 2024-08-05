package com.example.recipesapp.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {



    @Upsert
    suspend fun upsertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipes_table")
    fun getRecipes(): Flow<List<RecipeEntity>>


}