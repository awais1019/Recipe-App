package com.example.recipesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipesapp.databinding.IngredientsRowLayoutBinding
import com.example.recipesapp.models.ExtendedIngredient
import com.example.recipesapp.util.RecipeDiffUtil

class IngredientsAdapter:RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    private var ingredientList:List<ExtendedIngredient> = emptyList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
           return IngredientViewHolder(IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount()=ingredientList.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
          val singleIngredient=ingredientList[position]
           holder.bind(singleIngredient)
    }

    inner class IngredientViewHolder(private val binding: IngredientsRowLayoutBinding):RecyclerView.ViewHolder(binding.root)
    {
         fun bind(ingredient: ExtendedIngredient)
         {
             binding.ingredient=ingredient
             binding.executePendingBindings()
         }
    }

    fun updateIngredients(newIngredient:List<ExtendedIngredient>)
    {
        val diffUtil=RecipeDiffUtil(ingredientList,newIngredient)
        val diffResult=DiffUtil.calculateDiff(diffUtil)
        this.ingredientList=newIngredient
        diffResult.dispatchUpdatesTo(this)

    }
}