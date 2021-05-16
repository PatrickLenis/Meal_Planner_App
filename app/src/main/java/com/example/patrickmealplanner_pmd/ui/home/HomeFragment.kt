package com.example.patrickmealplanner_pmd.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patrickmealplanner_pmd.viewmodels.MainViewModel
import com.example.patrickmealplanner_pmd.R
import com.example.patrickmealplanner_pmd.adapters.RecipesAdapter
import com.example.patrickmealplanner_pmd.util.Constants.Companion.API_KEY
import com.example.patrickmealplanner_pmd.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.patrickmealplanner_pmd.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.patrickmealplanner_pmd.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.patrickmealplanner_pmd.util.NetworkResult
import com.example.patrickmealplanner_pmd.util.observeOnce
//import com.example.patrickmealplanner_pmd.util.observeOnce
import com.example.patrickmealplanner_pmd.viewmodels.RecipesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private  val args by navArgs<HomeFragmentArgs>()

    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }
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

        //mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setUpRecyclerView()//mView.shimmer_recycler_view.showShimmer()
        readDatabase()//requestApiData()

        val action_button = mView.findViewById<FloatingActionButton>(R.id.recipes_fab)
        action_button.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_recipesBottomSheet)
        }

        return mView
        //root
    }

    private fun setUpRecyclerView()
    {
        mView.shimmer_recycler_view.adapter = mAdapter
        mView.shimmer_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, Observer { database ->
                if(database.isNotEmpty() && !args.backFromBottomSheet){
                    Log.d("RecipesFragment", "readDatabase called")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                }else{
                    requestApiData()

                }
            })
        }
        /*mainViewModel.readRecipes.observe(viewLifecycleOwner, {database ->
            if(database.isNotEmpty()){
                Log.d("RecipesFragment", "readDatabase called")
                mAdapter.setData(database[0].foodRecipe)
                hideShimmerEffect()
            }else{
                requestApiData()
            }
        })*/
    }

    private fun requestApiData()
    {
        Log.d("RecipesFragment", "requestApiData called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipeRespons.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
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
        /*mainViewModel.recipeRespons.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
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
        })*/
    }

    /*private fun applyQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()

        queries["number"] = DEFAULT_RECIPES_NUMBER
        queries["apiKey"] = API_KEY
        queries["type"] = DEFAULT_MEAL_TYPE
        queries["diet"] = DEFAULT_DIET_TYPE
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }*/

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, Observer {database ->
                if(database.isNotEmpty()){
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
        /*mainViewModel.readRecipes.observe(viewLifecycleOwner, {database ->
            if(database.isNotEmpty()){
                mAdapter.setData(database[0].foodRecipe)
            }
        })*/
    }

    private fun showShimmerEffect(){
        mView.shimmer_recycler_view.showShimmer()
    }

    private fun hideShimmerEffect(){
        mView.shimmer_recycler_view.hideShimmer()
    }
}