package com.example.patrickmealplanner_pmd.data

import com.example.patrickmealplanner_pmd.data.nework.FoodRecipesAPI
import com.example.patrickmealplanner_pmd.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val foodRecipesAPI: FoodRecipesAPI
){

    suspend fun getRecipes(queries : Map<String, String>): Response<FoodRecipe>{
        return foodRecipesAPI.getRecipes(queries)
    }
}