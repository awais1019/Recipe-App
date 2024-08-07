package com.example.recipesapp.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.viewmodels.MainViewModel
import com.example.recipesapp.adapters.RecipeAdapter
import com.example.recipesapp.databinding.FragmentRecipesBinding
import com.example.recipesapp.util.Constants.Companion.API_KEY
import com.example.recipesapp.util.NetworkManager
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
    private lateinit var networkManager: NetworkManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner=this
        binding.mainViewModel=mainViewModel
        setupRecyclerView()
        getRecipes()
        setupToolbar()
        networkManager=NetworkManager(requireContext())
        observeNetworkStatus()
        setupFabClickListener()
       /* setupSearchMenu()*/

        return binding.root
    }

    private fun setupFabClickListener() {
        binding.fabRecipes.setOnClickListener {
            if(recipeViewModel.networkStatus)
            {
                findNavController().navigate(R.id.action_recipesFragment_to_bottomSheetFragment)
            }
            else
            {
                recipeViewModel.showNetworkStatus(requireContext())
            }
        }
    }


    private fun readDatabase() {

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
    private fun observeNetworkStatus()
    {
         recipeViewModel.readBackOnline.observe(viewLifecycleOwner){
             recipeViewModel.backOnline=it
    }

        lifecycleScope.launch {
            networkManager.isNetworkAvailable().collect()
            {
                recipeViewModel.networkStatus=it
                recipeViewModel.showNetworkStatus(requireContext())
                readDatabase()

            }
        }
    }


    private fun setupToolbar() {
        val toolbar: Toolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun setupSearchMenu() {
        setupToolbar()
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.recipe_search, menu) // Ensure `R.menu.recipe_search` exists

                val searchItem = menu.findItem(R.id.search_bar) // Ensure `R.id.search_bar` exists in your menu
                val searchView = searchItem.actionView as SearchView

                searchView.queryHint = "Search here..."
                searchView.isSubmitButtonEnabled = true // Enable the submit button

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        // Handle the query submission
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        // Handle text changes
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle other menu item selections if necessary
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}