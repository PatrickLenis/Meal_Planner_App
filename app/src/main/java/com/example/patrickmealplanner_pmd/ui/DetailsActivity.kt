package com.example.patrickmealplanner_pmd.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import coil.load
import com.example.patrickmealplanner_pmd.R
import com.example.patrickmealplanner_pmd.data.database.entities.FavoritesEntity
import com.example.patrickmealplanner_pmd.util.observeOnce
import com.example.patrickmealplanner_pmd.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*
import org.jsoup.Jsoup
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    private val mainViewModel: MainViewModel by viewModels()

    private var savedId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val backButton = findViewById<Button>(R.id.back_to_recipes_button)
        backButton.setOnClickListener { finish() }

        fun showSnackBar(message: String){
            Snackbar.make(
                    details_layout,
                    message,
                    Snackbar.LENGTH_SHORT
            ).setAction("OK"){}
                    .show()
        }

        var recipeExists: Boolean = false

        val planButton = findViewById<Button>(R.id.add_to_plan_button)

        //check if recipe is already added
        mainViewModel.readFavoriteRecipes.observe(this, Observer { favoritesEntity ->
            try{
                for(savedRecipe in favoritesEntity){
                    if(savedRecipe.result.id == args.result.id){
                        recipeExists = true//Log.d("CHECKER", "Called")
                        savedId = savedRecipe.id
                        planButton.text = "REMOVE FROM PLAN"
                        //planButton.background = resources.getDrawable(R.drawable.app_button_red)
                    }
                }
            }catch (e : Exception){
                Log.d("DetailsActivity", e.message.toString())
            }

        })
        //

        fun removeRecipeFromFavorites()
        {
            val favoritesEntity = FavoritesEntity(savedId, args.result)
            mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        }

        planButton.setOnClickListener {

            val favoritesEntity = FavoritesEntity(0, args.result)

            if(recipeExists){

                removeRecipeFromFavorites()

                //showSnackBar("Removed From Plan")
                planButton.text = "ADD TO PLAN"

                recipeExists = false
            }else {
                //showSnackBar("Added To Plan")
                planButton.text = "REMOVE FROM PLAN"

                mainViewModel.insertFavoriteRecipe(favoritesEntity)

                recipeExists = true
            }

        }

        val title = findViewById<TextView>(R.id.details_recipe_title)
        title.text = args.result.title.toString()

        val description = findViewById<TextView>(R.id.details_description)
        description.text = Jsoup.parse(args.result.summary).text().toString()

        val image = findViewById<ImageView>(R.id.details_imageView)
        image.load(args.result.image)
    }

    /*private fun checkSavedRecipes(menuItem: MenuItem){
        mainViewModel.readFavoriteRecipes.observe(this, Observer { favoritesEntity ->
            try{
                for(savedRecipe in favoritesEntity){
                    if(savedRecipe.result.id == args.result.id){
                        Log.d("CHECKER", "Called")
                    }
                }
            }catch (e : Exception){
                Log.d("DetailsActivity", e.message.toString())
            }

        })
    }
    private fun checkSavedRecipes(){
        val planButton = findViewById<Button>(R.id.add_to_plan_button)
        planButton.text = "REMOVE FROM PLAN OK 2"
        mainViewModel.readFavoriteRecipes.observe(this, Observer { favoritesEntity ->
            try{
                for(savedRecipe in favoritesEntity){
                    if(savedRecipe.result.id == args.result.id){
                        planButton.text = "REMOVE FROM PLAN"
                    }
                    planButton.text = "REMOVE FROM PLAN OK"
                }
            }catch (e : Exception){
                Log.d("DetailsActivity", e.message.toString())
            }

        })
    }*/

}