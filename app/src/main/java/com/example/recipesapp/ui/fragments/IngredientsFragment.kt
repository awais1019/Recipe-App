package com.example.recipesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipesapp.R
import com.example.recipesapp.adapters.IngredientsAdapter
import com.example.recipesapp.databinding.FragmentIngredientsBinding
import com.example.recipesapp.databinding.FragmentOverViewBinding
import com.example.recipesapp.models.Result


class IngredientsFragment : Fragment() {

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
     private var adapter: IngredientsAdapter=IngredientsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentIngredientsBinding.inflate(inflater,container,false)
        setAdapter()
        var args=arguments
        val result: Result? = arguments?.getParcelable("recipeBundle")
        result?.extendedIngredients?.let {
            adapter.updateIngredients(it)
        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null

    }

    private fun setAdapter()
    {
        binding.recycleViewIngredients.adapter=adapter
        binding.recycleViewIngredients.layoutManager=LinearLayoutManager(requireContext())
    }


}