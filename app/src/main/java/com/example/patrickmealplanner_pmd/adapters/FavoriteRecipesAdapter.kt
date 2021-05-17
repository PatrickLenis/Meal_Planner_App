package com.example.patrickmealplanner_pmd.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.patrickmealplanner_pmd.data.database.entities.FavoritesEntity
import com.example.patrickmealplanner_pmd.databinding.FavoritesRowLayoutBinding
import com.example.patrickmealplanner_pmd.ui.dashboard.DashboardFragmentDirections
import com.example.patrickmealplanner_pmd.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.favorites_row_layout.view.*

class FavoriteRecipesAdapter: RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    private var favoriteRecipes = emptyList<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoritesRowLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(favoritesEntity: FavoritesEntity){
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoritesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRecipe = favoriteRecipes[position]
        holder.bind(selectedRecipe)

        holder.itemView.favoriteRecipeRowLayout.setOnClickListener {
            val action = DashboardFragmentDirections.actionNavigationDashboardToDetailsActivity(selectedRecipe.result)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>){
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)

        favoriteRecipes = newFavoriteRecipes

        diffUtilResult.dispatchUpdatesTo(this)
    }

}