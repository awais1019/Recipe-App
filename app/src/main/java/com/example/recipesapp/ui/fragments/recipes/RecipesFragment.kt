package com.example.recipesapp.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.recipesapp.MainViewModel
import com.example.recipesapp.R
import com.example.recipesapp.RecipeAdapter
import com.example.recipesapp.util.Constants.Companion.API_KEY
import com.example.recipesapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.http.QueryMap
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var adapter= lazy { RecipeAdapter() }

    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        getRecipes()
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    private fun getRecipes()
    {
      mainViewModel.getRecipes(getQueries())
        mainViewModel.response.observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResult.Success->
               it.data?.let { it1 -> adapter.value.updateRecipeList(it1) }
                is NetworkResult.Error ->Toast.makeText(requireContext(),it.msg,Toast.LENGTH_SHORT).show()
                is NetworkResult.Loading -> Toast.makeText(requireContext(),"Loading",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getQueries(): HashMap<String, String> {

        val queries=HashMap<String,String>()
         queries["number"]="50"
         queries["apiKey"]=API_KEY
         queries["type"]="snack"
         queries["diet"]="Vegan"
         queries["addRecipeInformation"]="true"
         queries["fillIngredients"]="true"
        return queries
    }


}