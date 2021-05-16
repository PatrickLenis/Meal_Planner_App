package com.example.patrickmealplanner_pmd.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.navArgs
import coil.load
import com.example.patrickmealplanner_pmd.R
import org.jsoup.Jsoup

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val backButton = findViewById<Button>(R.id.back_to_recipes_button)
        backButton.setOnClickListener { finish() }

        val title = findViewById<TextView>(R.id.details_recipe_title)
        title.text = args.result.title.toString()

        val description = findViewById<TextView>(R.id.details_description)
        description.text = Jsoup.parse(args.result.summary).text().toString()

        val image = findViewById<ImageView>(R.id.details_imageView)
        image.load(args.result.image)
    }

}