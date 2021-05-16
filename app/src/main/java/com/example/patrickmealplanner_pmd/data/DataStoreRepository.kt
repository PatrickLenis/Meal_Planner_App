package com.example.patrickmealplanner_pmd.data

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import com.example.patrickmealplanner_pmd.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.patrickmealplanner_pmd.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.patrickmealplanner_pmd.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.patrickmealplanner_pmd.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.patrickmealplanner_pmd.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.patrickmealplanner_pmd.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.patrickmealplanner_pmd.util.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context){

    private object PreferenceKeys{
        val selecteMealType = preferencesKey<String>(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = preferencesKey<Int>(PREFERENCES_MEAL_TYPE_ID)

        val selectedDieType = preferencesKey<String>(PREFERENCES_DIET_TYPE)
        val selectedDietTypeID = preferencesKey<Int>(PREFERENCES_DIET_TYPE_ID)
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int){
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selecteMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDieType] = dietType
            preferences[PreferenceKeys.selectedDietTypeID] = dietTypeId
        }
    }

    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selecteMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDieType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeID = preferences[PreferenceKeys.selectedDietTypeID] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeID
            )
        }

}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
){

}