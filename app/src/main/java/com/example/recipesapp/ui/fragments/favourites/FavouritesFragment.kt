package com.example.recipesapp.ui.fragments.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.adapters.FavouriteRecipesAdapter
import com.example.recipesapp.databinding.ActivityDetailsBinding
import com.example.recipesapp.databinding.FragmentFavouritesBinding
import com.example.recipesapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel:MainViewModel by viewModels()
    private  val adapter=FavouriteRecipesAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         _binding=FragmentFavouritesBinding.inflate(inflater,container,false)
        setAdapter()

        binding.lifecycleOwner=this
        binding.mainViewModel=mainViewModel
        binding.adapter=adapter
        return binding.root
    }

    private fun updateData() {
        mainViewModel.readFavouriteRecipes.observe(viewLifecycleOwner)
        {
            adapter.updateList(it)
        }
    }

    private fun setAdapter()
    {
        binding.favouriteRecipesRecycleView.adapter=adapter
        binding.favouriteRecipesRecycleView.layoutManager=LinearLayoutManager(requireContext())
    }

}