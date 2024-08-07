package com.example.recipesapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.example.recipesapp.R
import com.example.recipesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        binding.bottomNav.setOnItemSelectedListener()
        {
            itemNavigation(it)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



}

    private fun itemNavigation(item: MenuItem):Boolean {

        val navController = findNavController(R.id.navHost)
       return when (item.itemId) {
            R.id.recipesFragment -> {
                navController.navigate(R.id.recipesFragment)
                true
            }
            R.id.favouritesFragment -> {
                navController.navigate(R.id.favouritesFragment)
                true

            }
           else -> {
               false
           }
        }
    }


}

