package com.example.recipesapp.di

import com.example.recipesapp.util.Constants.Companion.BASE_URL
import com.example.recipesapp.data.network.FoodRecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun getGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
       return  OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15,TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory):Retrofit
    {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

      @Singleton
      @Provides
      fun provideApiInterface(retrofit: Retrofit): FoodRecipeApi
    {
        return retrofit.create(FoodRecipeApi::class.java)
    }
}