package com.example.recipesapp.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.recipesapp.R
import com.example.recipesapp.databinding.FragmentBottomSheetBinding
import com.example.recipesapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.recipesapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.recipesapp.viewmodels.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment :BottomSheetDialogFragment() {

    private  var _binding: FragmentBottomSheetBinding?=null
    private val binding get() = _binding!!
    private val recipeViewModel: RecipeViewModel by viewModels()

    private var mealType= DEFAULT_MEAL_TYPE
    private var mealTypeId= 0
    private var dietType= DEFAULT_DIET_TYPE
    private var dietTypeId=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentBottomSheetBinding.inflate(inflater,container,false)
         chipListeners()
        btnListener()
        observe()



        return binding.root
    }

    private fun updateChip(selectedDietTypeId: Int, chipGroup: ChipGroup) {
        if (selectedDietTypeId!=0)
        {
            val chip=chipGroup.findViewById<Chip>(selectedDietTypeId)
            chip.isChecked=true
        }
    }
   private fun btnListener()
   {
       binding.btnApply.setOnClickListener()
       {
           recipeViewModel.saveMealAndDiet(mealType,mealTypeId,dietType,dietTypeId)
           val action=BottomSheetFragmentDirections.actionBottomSheetFragmentToRecipesFragment(true)
           findNavController().navigate(action)
       }
      
   }
    private fun observe()
    {
        recipeViewModel.readMealAndDiet.asLiveData().observe(viewLifecycleOwner) { value ->
            mealType = value.selectedMealType
            dietType = value.selectedDietType
            updateChip(value.selectedDietTypeId,binding.chipGroupDietType)
            updateChip(value.selectedMealTypeId,binding.chipGroupMealType)
        }
    }
    private fun chipListeners()
  {
      binding.chipGroupMealType.setOnCheckedStateChangeListener { group, checkedIds ->
          val chip: Chip =group.findViewById<Chip>(checkedIds.first())
          val selectedMealChipName=chip.text.toString().lowercase()
          mealType=selectedMealChipName
          mealTypeId=checkedIds.first()
      }
      binding.chipGroupDietType.setOnCheckedStateChangeListener{group,checkedIds->
          val chip: Chip =group.findViewById<Chip>(checkedIds.first())
          val selectedDietChipName=chip.text.toString().lowercase()
          dietType=selectedDietChipName
          dietTypeId=checkedIds.first()
      }
  }

}