package com.example.themealapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.themealapp.R
import com.example.themealapp.adapter.FoodListAdapter
import com.example.themealapp.viewmodel.FoodListViewModel
import kotlinx.android.synthetic.main.fragment_food_list.*

class FoodListFragment : Fragment() {

    private lateinit var viewModel: FoodListViewModel

    private var adapterFoodList = FoodListAdapter(this, arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var filterRegion = ""
        var filterCategory = ""
        arguments?.let {
            filterRegion = FoodListFragmentArgs.fromBundle(it).region
            filterCategory = FoodListFragmentArgs.fromBundle(it).category
        }

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        if(filterRegion != "0") {                           //Region bölümünden tıklandı.
            viewModel.getFoodFromRegionId(filterRegion)
        }
        else if(filterCategory != "0") {                     //Category bölümünden tıklandı.
            viewModel.getFoodFromCategoryId(filterCategory)
        }

        //favorilere eklenen yemeklerin kontrolü için çağrıldı.
        viewModel.getAllFavorite()

        foodListRecyler.adapter = adapterFoodList

        obserLiveData()
    }

    fun obserLiveData(){
        viewModel.mealList.observe(viewLifecycleOwner, Observer { list->
            list?.let {
                adapterFoodList.refreshList(it)
            }
        })
    }
}