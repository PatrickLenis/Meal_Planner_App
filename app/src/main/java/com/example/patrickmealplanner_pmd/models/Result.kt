package com.example.patrickmealplanner_pmd.models


import com.example.patrickmealplanner_pmd.models.ExtendedIngredient
import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("aggregateLikes")
    val aggregateLikes: Int,
        @SerializedName("cheap")
    val cheap: Boolean,
        val cuisines: List<String>,
        @SerializedName("dairyFree")
    val dairyFree: Boolean,
        @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
        @SerializedName("glutenFree")
    val glutenFree: Boolean,
        @SerializedName("id")
    val id: Int,
        @SerializedName("image")
    val image: String,
    //@SerializedName("license")
    //val license: String,
        @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
        @SerializedName("sourceName")
    val sourceName: String,
        @SerializedName("sourceUrl")
    val sourceUrl: String,
        @SerializedName("summary")
    val summary: String,
        @SerializedName("title")
    val title: String,
    /*@SerializedName("unusedIngredients")
    val unusedIngredients: List<Any>,
    @SerializedName("usedIngredientCount")
    val usedIngredientCount: Int,
    @SerializedName("usedIngredients")
    val usedIngredients: List<Any>,*/
    @SerializedName("vegan")
    val vegan: Boolean,
        @SerializedName("vegetarian")
    val vegetarian: Boolean,
        @SerializedName("veryHealthy")
    val veryHealthy: Boolean
    //@SerializedName("veryPopular")
    //val veryPopular: Boolean
)