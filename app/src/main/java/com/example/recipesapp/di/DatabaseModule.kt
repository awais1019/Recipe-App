package com.example.recipesapp.di

import android.content.Context
import androidx.room.Room
import com.example.recipesapp.database.RecipesDatabase
import com.example.recipesapp.util.Constants.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun getDatabase( @ApplicationContext context: Context): RecipesDatabase {
           return Room.databaseBuilder(context,RecipesDatabase::class.java,DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun getDao(database: RecipesDatabase)=database.getRecipeDao()
}