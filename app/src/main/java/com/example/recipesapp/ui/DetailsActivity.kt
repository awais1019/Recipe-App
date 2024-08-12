package com.example.recipesapp.ui

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navArgs
import com.example.recipesapp.R
import com.example.recipesapp.adapters.PagerAdapter
import com.example.recipesapp.database.FavouriteRecipeEntity
import com.example.recipesapp.databinding.ActivityDetailsBinding
import com.example.recipesapp.ui.fragments.IngredientsFragment
import com.example.recipesapp.ui.fragments.InstructionsFragment
import com.example.recipesapp.ui.fragments.OverViewFragment
import com.example.recipesapp.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()
    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!
    private val mainViewMode:MainViewModel by viewModels()
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
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_activity_toolbar_menu,menu)
        val item=menu?.findItem(R.id.star)
        isRecipeSaved(item)
        return true
    }

    private fun isRecipeSaved(item: MenuItem?) {
        mainViewMode.readFavouriteRecipes.observe(this)
        {favouriteRecipes->
            try {
                for(recipe in favouriteRecipes)
                    if(recipe.result.recipeId==args.result.recipeId)
                    {
                        changeColor(item!!,R.color.yellow)
                    }
            }
            catch (e:Exception)
            {
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when( item.itemId)
        {
            R.id.star ->
            {
                saveFavouriteRecipe(item)
                true
            }
            android.R.id.home ->
            {
                finish()
                false
            }
           else -> false

        }

    }
    private fun saveFavouriteRecipe(item: MenuItem)
    {
        mainViewMode.insertFavouriteRecipe(FavouriteRecipeEntity(result = args.result))
        changeColor(item,R.color.yellow)
        showSnackBar("Recipe Saved")
    }

    private fun showSnackBar(s: String) {
        Snackbar.make(binding.root,s,Snackbar.LENGTH_SHORT).setAction("OK"){}.show()
    }

    private fun changeColor(item: MenuItem, color: Int) {

        item.icon?.setTint(ContextCompat.getColor(this,color))

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
