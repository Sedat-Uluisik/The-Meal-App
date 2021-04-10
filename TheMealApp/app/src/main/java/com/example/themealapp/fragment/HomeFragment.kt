package com.example.themealapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.themealapp.R
import com.example.themealapp.adapter.CategoryAdapter
import com.example.themealapp.adapter.RegionAdapter
import com.example.themealapp.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private var loadingBar: ProgressBar?= null
    private var adapterRegion = RegionAdapter(arrayListOf())
    private var adapterCategory = CategoryAdapter(this, arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getRegionDataFromApi()
        viewModel.getCategoriesFromApi()

        loadingBar = view.findViewById(R.id.loadingBar)
        regionRecycler.adapter = adapterRegion
        gridView.adapter = adapterCategory

        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.region.observe(viewLifecycleOwner, Observer { region->
            region?.let {
                adapterRegion.refreshList(it.meals)
            }
        })
        viewModel.categories.observe(viewLifecycleOwner, Observer { categories->
            categories?.let {
                adapterCategory.refreshList(it.categories)
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if(it){
                    loadingBar!!.visibility = View.VISIBLE
                    linearLayoutRegion.visibility = View.INVISIBLE
                    gridView.visibility = View.INVISIBLE
                }else{
                    loadingBar!!.visibility = View.GONE
                    linearLayoutRegion.visibility = View.VISIBLE
                    gridView.visibility = View.VISIBLE
                }
            }
        })
    }


}