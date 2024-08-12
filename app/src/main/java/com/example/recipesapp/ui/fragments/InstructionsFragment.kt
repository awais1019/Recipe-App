package com.example.recipesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.recipesapp.R
import com.example.recipesapp.databinding.FragmentIngredientsBinding
import com.example.recipesapp.databinding.FragmentInstructionsBinding
import com.example.recipesapp.models.Result


class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding=FragmentInstructionsBinding.inflate(inflater,container,false)
        var args=arguments
        val result: Result? = arguments?.getParcelable("recipeBundle")
        binding.webView.webViewClient= object :WebViewClient() {}
        val url: String =result!!.sourceUrl
        binding.webView.loadUrl(url)

        return binding.root
    }


}