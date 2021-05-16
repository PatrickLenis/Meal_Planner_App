package com.example.patrickmealplanner_pmd.data.database

import androidx.room.TypeConverter
import com.example.patrickmealplanner_pmd.models.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String{
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe{
        val listType = object : TypeToken<FoodRecipe>() {}.type
        return gson.fromJson(data, listType)
    }

}