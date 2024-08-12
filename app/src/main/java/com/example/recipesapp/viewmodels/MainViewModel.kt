package com.example.recipesapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.data.Repository
import com.example.recipesapp.database.FavouriteRecipeEntity
import com.example.recipesapp.database.RecipeEntity
import com.example.recipesapp.models.FoodRecipe
import com.example.recipesapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

   //Room Database
    val readRecipes:LiveData<List<RecipeEntity>> = repository.local.getRecipes().asLiveData()

    val readFavouriteRecipes =repository.local.getFavouriteRecipes().asLiveData()


    private fun insertRecipes(recipeEntity: RecipeEntity)=viewModelScope.launch {
        repository.local.insertRecipes(recipeEntity)
    }

    fun insertFavouriteRecipe(favouriteRecipeEntity: FavouriteRecipeEntity)
    {
        viewModelScope.launch {
            repository.local.insertFavouriteRecipes(favouriteRecipeEntity)
        }
    }

    fun deleteFavouriteRecipe(favouriteRecipeEntity: FavouriteRecipeEntity)
    {
        viewModelScope.launch {
            repository.local.deleteFavouriteRecipe(favouriteRecipeEntity)
        }
    }

    fun deleteAllFavouriteRecipes()
    {
        viewModelScope.launch {
            repository.local.deleteAllFavouriteRecipes()
        }
    }


    //Retrofit
    private val recipesResponse = MutableLiveData<NetworkResult<FoodRecipe>>()
    val response  get() = recipesResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
          recipesSafeCall(queries)
    }

    private fun handleRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
         when {
             response.message().toString().contains("timeout")->
             {
                 return NetworkResult.Error("Timeout")
             }
             response.code()==402->
             {
                 return NetworkResult.Error("Api key Limited")
             }
             response.body()!!.results.isNullOrEmpty()->
             {
                 return  NetworkResult.Error("Recipe not found")
             }
             response.isSuccessful-> {
                 val res = response.body()
                 return NetworkResult.Success(res!!)
             }
             else->
                 return NetworkResult.Error(response.message())
         }
    }

    private suspend fun recipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value=NetworkResult.Loading()
        if (hasInternetConnection())
        {try
        {
            val response=repository.remote.getRecipes(queries)
            recipesResponse.value=handleRecipesResponse(response)

            val responseData=recipesResponse.value!!.data
            if (responseData!=null)
            {
                offlineCacheRecipes(responseData)
            }
        }
        catch (e:Exception)
        {
            recipesResponse.value=NetworkResult.Error("Recipes Not Found")
        }

        }
        else
        {
            recipesResponse.value=NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(responseData: FoodRecipe) {
          val recipeEntity=RecipeEntity(responseData)
        insertRecipes(recipeEntity)
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }

    }
}
