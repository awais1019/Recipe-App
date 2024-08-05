package com.example.recipesapp.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.viewmodels.MainViewModel
import com.example.recipesapp.adapters.RecipeAdapter
import com.example.recipesapp.databinding.FragmentRecipesBinding
import com.example.recipesapp.util.Constants.Companion.API_KEY
import com.example.recipesapp.util.NetworkResult
import com.example.recipesapp.util.observeOnce
import com.example.recipesapp.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private val args: RecipesFragmentArgs by navArgs()
    private val recipeViewModel: RecipeViewModel by viewModels()
    private var adapter=  RecipeAdapter()
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        binding.mainViewModel=mainViewModel
        setupRecyclerView()
        getRecipes()
        binding.fabRecipes.setOnClickListener {
            findNavController().navigate(R.id.bottomSheetFragment)
        }
        return binding.root
    }


    fun readDatabase() {

        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner)
            {
                if (it.isNotEmpty()&&!args.backFromBottomSheet) {
                    hideShimmer()
                    adapter.updateRecipeList(it[0].foodRecipe)
                } else {
                    getRecipes()
                }

            }
        }
    }
    private fun getRecipes()
    {
      mainViewModel.getRecipes(recipeViewModel.getQueries())
        mainViewModel.response.observe(viewLifecycleOwner)
        {
            when(it)
            {
                is NetworkResult.Success->
                {
                    it.data?.let { it1 -> adapter.updateRecipeList(it1)}
                    hideShimmer()
               }
                is NetworkResult.Error -> {

                    hideShimmer()
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                    offlineCache()
                }
                is NetworkResult.Loading -> showShimmer()
            }
        }
    }

    private fun showShimmer() {
        binding.shimmerContainer.visibility = View.VISIBLE
        binding.shimmerContainer.startShimmer()
        binding.recycleView.visibility = View.GONE
    }

    private fun hideShimmer() {
        binding.shimmerContainer.visibility = View.GONE
        binding.shimmerContainer.stopShimmer()
        binding.recycleView.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        binding.recycleView.adapter=adapter
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    private fun offlineCache() {

        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner)
            {
                if (it.isNotEmpty()) {
                    adapter.updateRecipeList(it[0].foodRecipe)
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}