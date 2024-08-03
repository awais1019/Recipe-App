package com.example.recipesapp.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
@ActivityRetainedScoped
class Repository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

   val remote=remoteDataSource


}