package com.example.recipesapp.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.recipesapp.R
import com.example.recipesapp.databinding.FragmentOverViewBinding
import com.example.recipesapp.models.Result
import org.jsoup.Jsoup

class OverViewFragment : Fragment() {
    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverViewBinding.inflate(inflater, container, false)

        val result: Result? = arguments?.getParcelable("recipeBundle")
        result?.let {
            setupUI(it)
        }

        return binding.root
    }

    private fun setupUI(result: Result) {
        loadBasicInfo(result)
        loadAttributes(result)
    }

    private fun loadBasicInfo(result: Result) {
        binding.mainImageView.load(result.imageType)
        binding.textViewTitle.text = result.title
        binding.textViewTimeValue.text = result.readyInMinutes.toString()
        binding.textViewLikesValue.text = result.aggregateLikes.toString()

        val summary=Jsoup.parse(result.summary).text()
        binding.textViewSummary.text = summary
    }

    private fun loadAttributes(result: Result) {
        val greenColor = ContextCompat.getColor(requireContext(), R.color.green)
        if (result.vegetarian) {
            binding.imageViewVegetarian.setColorFilter(greenColor)
            binding.textViewVegetarian.setTextColor(greenColor)
        }

        if (result.cheap) {
            binding.imageViewCheap.setColorFilter(greenColor)
            binding.textViewCheap.setTextColor(greenColor)
        }

        if (result.veryHealthy) {
            binding.imageViewHealthy.setColorFilter(greenColor)
            binding.textViewHealthy.setTextColor(greenColor)
        }

        if (result.glutenFree) {
            binding.imageViewGlutenFree.setColorFilter(greenColor)
            binding.textViewGlutenFree.setTextColor(greenColor)
        }

        if (result.vegan) {
            binding.imageViewVegan.setColorFilter(greenColor)
            binding.textViewVegan.setTextColor(greenColor)
        }

        if (result.dairyFree) {
            binding.imageViewDiaryFree.setColorFilter(greenColor)
            binding.textViewDiaryFree.setTextColor(greenColor)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
