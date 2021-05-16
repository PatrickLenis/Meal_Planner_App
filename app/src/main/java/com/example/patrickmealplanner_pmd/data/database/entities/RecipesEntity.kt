package com.example.patrickmealplanner_pmd.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.patrickmealplanner_pmd.models.FoodRecipe
import com.example.patrickmealplanner_pmd.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
        var foodRecipe: FoodRecipe
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}