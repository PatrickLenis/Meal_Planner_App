package com.example.patrickmealplanner_pmd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patrickmealplanner_pmd.MainViewModel
import com.example.patrickmealplanner_pmd.R
import com.example.patrickmealplanner_pmd.adapters.RecipesAdapter
import com.example.patrickmealplanner_pmd.util.Constants.Companion.API_KEY
import com.example.patrickmealplanner_pmd.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        mView = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = mView.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setUpRecyclerView()//mView.shimmer_recycler_view.showShimmer()
        requestApiData()

        return mView
        //root
    }

    private fun requestApiData()
    {
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipeRespons.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    //loadDataFromCache()
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun applyQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()

        queries["number"] = "3"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    private fun setUpRecyclerView()
    {
        mView.shimmer_recycler_view.adapter = mAdapter
        mView.shimmer_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        mView.shimmer_recycler_view.showShimmer()
    }

    private fun hideShimmerEffect(){
        mView.shimmer_recycler_view.hideShimmer()
    }
}