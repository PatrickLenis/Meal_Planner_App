package com.example.patrickmealplanner_pmd.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.example.patrickmealplanner_pmd.R

class RecipesRowBinding {

    companion object{

        @BindingAdapter("loadImageFromURL")
        @JvmStatic
        fun loadImageFromURL(imageView: ImageView, imageURL: String)
        {
            imageView.load(imageURL){
                crossfade(600)
            }
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic
        fun setNumberOfMinutes(textView: TextView, minutes: Int)
        {
            textView.text = minutes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean)
        {
            if(vegan)
            {
                when(view)
                {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.accent_green))
                        view.text = "Vegan"
                    }

                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.accent_green))
                    }
                }
            }
            else
            {
                when(view)
                {
                    is TextView -> {
                        view.setTextColor(ContextCompat.getColor(view.context, R.color.accent_red))
                        view.text = "Not Vegan"
                    }

                    is ImageView -> {
                        view.setColorFilter(ContextCompat.getColor(view.context, R.color.accent_red))
                    }
                }
            }
        }

    }

}