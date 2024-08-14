package com.example.recipesapp.ui.fragments.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.adapters.FavouriteRecipesAdapter
import com.example.recipesapp.databinding.ActivityDetailsBinding
import com.example.recipesapp.databinding.FragmentFavouritesBinding
import com.example.recipesapp.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel:MainViewModel by viewModels()
    private  var adapter:FavouriteRecipesAdapter= FavouriteRecipesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         _binding=FragmentFavouritesBinding.inflate(inflater,container,false)
        setAdapter()
        binding.lifecycleOwner=this
        binding.mainViewModel=mainViewModel
        menuClick()
        binding.adapter=adapter
        return binding.root
    }



    private fun setAdapter()
    {
        binding.favouriteRecipesRecycleView.adapter=adapter
        binding.favouriteRecipesRecycleView.layoutManager=LinearLayoutManager(requireContext())
    }



    private fun showNoItemsMessage(msg:String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

    private fun menuClick()
    {

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.allDelete -> {
                    mainViewModel.readFavouriteRecipes.observe(viewLifecycleOwner) { favouriteRecipes ->
                        if (favouriteRecipes.isNullOrEmpty()) {
                            showNoItemsMessage("No Recipe to delete")
                        } else {
                            mainViewModel.deleteAllFavouriteRecipes()
                            showNoItemsMessage("All Recipes Deleted")
                        }
                    }
                    true
                }
                else -> false
            }
        }

    }


}