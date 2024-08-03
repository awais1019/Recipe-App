package com.example.recipesapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.data.Repository
import com.example.recipesapp.models.FoodRecipe
import com.example.recipesapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application // Injecting Application
) : AndroidViewModel(application) {

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
