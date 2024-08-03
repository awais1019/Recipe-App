package com.example.recipesapp.data

import com.example.recipesapp.data.network.FoodRecipeApi
import com.example.recipesapp.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val foodRecipeApi: FoodRecipeApi)
{
    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipeApi.getRecipes(queries)
    }



}


