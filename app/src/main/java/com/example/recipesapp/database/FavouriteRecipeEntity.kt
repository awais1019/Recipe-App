package com.example.recipesapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipesapp.models.Result


@Entity(tableName = "favourite_recipes_table")
 data class FavouriteRecipeEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,
    var result:Result
)
