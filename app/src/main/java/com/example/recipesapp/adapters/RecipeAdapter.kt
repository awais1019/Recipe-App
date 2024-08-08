package com.example.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.RecycleviewItemDesignBinding
import com.example.recipesapp.models.FoodRecipe
import com.example.recipesapp.models.Result
import com.example.recipesapp.util.RecipeDiffUtil

class RecipeAdapter(
    private var recipeList: List<Result> = emptyList<Result>()
):RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            RecycleviewItemDesignBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount()=recipeList.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentRecipe=recipeList[position]
        holder.bind(currentRecipe)
     }

    inner class RecipeViewHolder(private var binding: RecycleviewItemDesignBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result)
        {
             binding.result=result
             binding.executePendingBindings()
        }


        }

    fun updateRecipeList(newData:FoodRecipe)
    {
        val recipeDiffUtil = RecipeDiffUtil(recipeList,newData.results)
        val diffUtilResult= DiffUtil.calculateDiff(recipeDiffUtil)
        this.recipeList=newData.results
        diffUtilResult.dispatchUpdatesTo(this)

    }
}