package com.example.patrickmealplanner_pmd.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.patrickmealplanner_pmd.data.Repository
import com.example.patrickmealplanner_pmd.data.database.RecipesEntity
import com.example.patrickmealplanner_pmd.models.FoodRecipe
import com.example.patrickmealplanner_pmd.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(
        private val repository: Repository,
        application : Application
) : AndroidViewModel(application){

    //Room Database
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertRecipes(recipesEntity)
    }

    //Retrofit
    var recipeRespons : MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipeRespons.value = NetworkResult.Loading()
        if(hasInternetConnection()){
            try{
                val response = repository.remote.getRecipes(queries)
                recipeRespons.value = handleFoodRecipesResponse(response)

                val foodRecipe = recipeRespons.value!!.data
                if(foodRecipe != null){
                    offlineCacheRecipes(foodRecipe)
                }
            }catch (e : Exception){
                recipeRespons.value = NetworkResult.Error("Recipes Not Found")
            }
        }else{
            recipeRespons.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when{
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("Api Key Limited")
            }
            response.body()!!.results.isNullOrEmpty() ->
            {
                return NetworkResult.Error("Recipes Not Found")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    fun hasInternetConnection(): Boolean{
        val conectivityManager = getApplication<Application>().getSystemService(
                Context.CONNECTIVITY_SERVICE
        )as ConnectivityManager

        val activeNetwork = conectivityManager.activeNetwork ?: return false
        val capabilities = conectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}