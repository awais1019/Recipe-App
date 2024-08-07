package com.example.recipesapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipesapp.util.Constants.Companion.BACK_ONLINE
import com.example.recipesapp.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.recipesapp.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.recipesapp.util.Constants.Companion.PREFERENCES_NAME
import com.example.recipesapp.util.Constants.Companion.PREFERENCE_DIET_TYPE
import com.example.recipesapp.util.Constants.Companion.PREFERENCE_DIET_TYPE_ID
import com.example.recipesapp.util.Constants.Companion.PREFERENCE_MEAL_TYPE
import com.example.recipesapp.util.Constants.Companion.PREFERENCE_MEAL_TYPE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

@Singleton
class DataStoreRepository @Inject constructor (@ApplicationContext context: Context) {


    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCE_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCE_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCE_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCE_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(BACK_ONLINE)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun storeBackOnline(online: Boolean) {
        dataStore.edit {
            it[PreferenceKeys.backOnline] = online
        }

    }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }

        }.map {
            val backOnline = it[PreferenceKeys.backOnline] ?: false
            backOnline
        }



    suspend fun saveMealAndDietType(
        selectedMealType: String,
        selectedMealTypeId: Int,
        selectedDietType: String,
        selectedDietTypeId: Int
    ) {
        dataStore.edit {
            it[PreferenceKeys.selectedMealType] = selectedMealType
            it[PreferenceKeys.selectedMealTypeId] = selectedMealTypeId
            it[PreferenceKeys.selectedDietType] = selectedDietType
            it[PreferenceKeys.selectedDietTypeId] = selectedDietTypeId
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }
        .map {
            val selectedMealType = it[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = it[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = it[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = it[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

}
data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int

)
