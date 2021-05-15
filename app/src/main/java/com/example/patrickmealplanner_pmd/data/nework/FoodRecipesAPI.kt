package com.example.patrickmealplanner_pmd.data.nework

import com.example.patrickmealplanner_pmd.models.FoodRecipe
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.Response

interface FoodRecipesAPI {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
            @QueryMap querys : Map<String, String>
    ):Response<FoodRecipe>
}