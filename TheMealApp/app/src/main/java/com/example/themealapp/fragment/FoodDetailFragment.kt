package com.example.themealapp.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.themealapp.R
import com.example.themealapp.clicklistener.ClickListener
import com.example.themealapp.databinding.FragmentFoodDetailBinding
import com.example.themealapp.model.favourite.Favourite
import com.example.themealapp.viewmodel.FoodDetailViewModel
import com.example.themealapp.viewmodel.FoodListViewModel

class FoodDetailFragment : Fragment(), ClickListener {

    private lateinit var viewModelDetail: FoodDetailViewModel
    private lateinit var viewModelFoodList: FoodListViewModel
    private lateinit var dataBinding: FragmentFoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_food_detail, container, false)
        viewModelDetail = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModelFoodList = ViewModelProviders.of(this).get(FoodListViewModel::class.java)  //Favori kontrolü için.
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mealId = ""
        arguments?.let {
            mealId = FoodDetailFragmentArgs.fromBundle(it).foodId
        }

        dataBinding.clickListener = this

        if(mealId != "0")
            viewModelDetail.getFoodDetailFromApi(mealId)

        viewModelFoodList.getAllFavorite()

        checkFavorite(view.context, mealId) //başlangıçta favori ikon kontrolü.

        observeLiveData(view.context, mealId)
    }

    private fun observeLiveData(context: Context, mealId: String){
        viewModelDetail.foodDetail.observe(viewLifecycleOwner, Observer { food->
            food?.let {
                dataBinding.foodDetail = it.meals[0]
            }
        })
        viewModelFoodList.favorites.observe(viewLifecycleOwner, Observer {id_->  //favori durumu değişince ikon değişimi.
            id_?.let {
                var isFavorite = false
                for(id in it){
                    if(mealId == id.mealId) {
                        isFavorite = true
                        break
                    }
                }
                if(isFavorite){  //favorilerde varsa ikon aktif olsun.
                    dataBinding.favoriteIcon = ContextCompat.getDrawable(context, R.drawable.favourite_icon)
                }else{ //favorilerde yoksa ikon sönsün.
                    dataBinding.favoriteIcon = ContextCompat.getDrawable(context, R.drawable.un_favourite_icon)
                }
            }
        })
    }

    override fun onFavoriteClick(view: View, mealId: String) {
        val favList = viewModelFoodList.favorites.value
        var isFavorite = false
        if(favList != null){
            for(id in favList){  //room db den favorilenmiş yemeklerin id'lerini getir.
                if(mealId == id.mealId) {
                    isFavorite = true
                    break
                }
            }
            if(isFavorite){  //Yemek favoride ise, favorilerden kaldırılır.
                val fav = Favourite(mealId)
                viewModelFoodList.deleteFavorite(fav)
            }else{ //Yemek favorilerde yoksa eklenir.
                val fav = Favourite(mealId)
                viewModelFoodList.saveFavorite(fav)
            }
        }
    }

    private fun checkFavorite(context: Context, mealId: String){
        val favList = viewModelFoodList.favorites.value
        var isFavorite = false
        if(favList != null){
            for(id in favList){  //room db den favorilenmiş yemeklerin id'lerini getir
                if(mealId == id.mealId) {
                    isFavorite = true
                    break
                }
            }
            if(isFavorite){
                dataBinding.favoriteIcon = ContextCompat.getDrawable(context, R.drawable.favourite_icon)
            }else{
                dataBinding.favoriteIcon = ContextCompat.getDrawable(context, R.drawable.un_favourite_icon)
            }
        }
    }
}