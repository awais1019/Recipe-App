package com.example.recipesapp.adapters

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.R
import com.example.recipesapp.database.FavouriteRecipeEntity
import com.example.recipesapp.databinding.FavouriteRecipesRowLayoutBinding
import com.example.recipesapp.ui.fragments.favourites.FavouritesFragment
import com.example.recipesapp.ui.fragments.favourites.FavouritesFragmentDirections
import com.example.recipesapp.util.RecipeDiffUtil
import com.example.recipesapp.viewmodels.MainViewModel

class FavouriteRecipesAdapter(

):RecyclerView.Adapter<FavouriteRecipesAdapter.MyViewHolder>(){

    private var favouriteRecipes= emptyList<FavouriteRecipeEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(FavouriteRecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()=favouriteRecipes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val favourite=favouriteRecipes[position]
        holder.bind(favourite)

       holder.binding.root.setOnClickListener {
               val action=FavouritesFragmentDirections.actionFavouritesFragmentToDetailsActivity(favourite.result)
               holder.binding.root.findNavController().navigate(action)

        }


    }

    class MyViewHolder(var binding: FavouriteRecipesRowLayoutBinding):RecyclerView.ViewHolder(binding.root) {


        fun bind(favouriteRecipeEntity: FavouriteRecipeEntity)
        {
            binding.recipeEntity=favouriteRecipeEntity
            binding.executePendingBindings()
        }

    }
    fun updateList(favouriteRecipe:List<FavouriteRecipeEntity>)
    {
        val diffUtil=RecipeDiffUtil(favouriteRecipes,favouriteRecipe)
        val result=DiffUtil.calculateDiff(diffUtil)
        this.favouriteRecipes=favouriteRecipe
        result.dispatchUpdatesTo(this)

    }


}