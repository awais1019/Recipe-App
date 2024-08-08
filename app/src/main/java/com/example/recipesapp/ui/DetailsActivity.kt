package com.example.recipesapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.navArgs
import com.example.recipesapp.R
import com.example.recipesapp.adapters.PagerAdapter
import com.example.recipesapp.databinding.ActivityDetailsBinding
import com.example.recipesapp.ui.fragments.IngredientsFragment
import com.example.recipesapp.ui.fragments.InstructionsFragment
import com.example.recipesapp.ui.fragments.OverViewFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWindowInsets()
        setupToolbar()
        setupViewPagerAndTabs()
    }

    private fun setupWindowInsets() {

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupToolbar() {

        binding.toolbar.title = "Details"
    }

    private fun setupViewPagerAndTabs() {

        val fragments = listOf(
            OverViewFragment(),
            IngredientsFragment(),
            InstructionsFragment()
        )

        val titles = listOf(
            "Overview",
            "Ingredients",
            "Instructions"
        )


        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)


        binding.viewPager.adapter = PagerAdapter(
            resultBundle,
            fragments,
            titles,
            this
        )

        // Link the TabLayout with the ViewPager2 using TabLayoutMediator
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
