package com.example.patrickmealplanner_pmd.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.patrickmealplanner_pmd.R
import com.example.patrickmealplanner_pmd.adapters.FavoriteRecipesAdapter
import com.example.patrickmealplanner_pmd.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private val mAdapter: FavoriteRecipesAdapter by lazy { FavoriteRecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        setupRecyclerView(root.favoriteRecipesRecyclerView)

        mainViewModel.readFavoriteRecipes.observe(viewLifecycleOwner, Observer { favoritesEntity ->
            mAdapter.setData(favoritesEntity)
        })

        return root
    }

    private fun setupRecyclerView(recyclerView: RecyclerView){
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}