package com.example.themealapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.themealapp.R
import com.example.themealapp.clicklistener.ClickListener
import com.example.themealapp.databinding.LayoutFoodItemBinding
import com.example.themealapp.fragment.FoodListFragmentDirections
import com.example.themealapp.model.favourite.Favourite
import com.example.themealapp.model.food.Meal
import com.example.themealapp.viewmodel.FoodListViewModel

class FoodListAdapter(private val fragment: Fragment,
                      private val list: ArrayList<Meal>): RecyclerView.Adapter<FoodListAdapter.Holder>(), ClickListener {

    private lateinit var viewModel: FoodListViewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListAdapter.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<LayoutFoodItemBinding>(inflater, R.layout.layout_food_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FoodListAdapter.Holder, position: Int) {
        val food = list[position]

        holder.item.food = food
        holder.item.clickListener = this

        //Favori yemek ikonu kontrolü.
        viewModel = ViewModelProviders.of(fragment).get(FoodListViewModel::class.java)
        viewModel.favorites.observe(fragment.viewLifecycleOwner, Observer {id_->  //favorilenmiş yemeklerin id'lerini getirir.
            id_?.let {
                var isFavorite = false
                for(id in it){
                    if(food.idMeal == id.mealId) {
                        isFavorite = true
                        break
                    }
                }
                if(isFavorite){  //favorilerde varsa ikon aktif.
                    holder.item.favoriteIcon = ContextCompat.getDrawable(holder.itemView.context, R.drawable.favourite_icon)
                }else{ //favorilerde yoksa ikon söner.
                    holder.item.favoriteIcon = ContextCompat.getDrawable(holder.itemView.context, R.drawable.un_favourite_icon)
                }
            }
        })
    }

    class Holder(var item: LayoutFoodItemBinding): RecyclerView.ViewHolder(item.root){

    }

    fun refreshList(list_: List<Meal>){
        list.clear()
        list.addAll(list_)
        notifyDataSetChanged()
    }

    //yemek listesindeki item'lere tıklama işlemi.
    override fun onItemClick(view: View, mealId: String) {
        val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(mealId)
        Navigation.findNavController(view).navigate(action)
    }

    //Yemek favorileme işlemi.
    override fun onFavoriteClick(view: View, mealId: String) {
        viewModel = ViewModelProviders.of(fragment).get(FoodListViewModel::class.java)
        val favList = viewModel.favorites.value
        var isFavorite = false
        if(favList != null){
            for(id in favList){  //room db den favorilenmiş yemeklerin id'lerini getir
                if(mealId == id.mealId) {
                    isFavorite = true
                    break
                }
            }
            if(isFavorite){  //Tıklanan yemek favoride ise, favorilerden kaldırılır.
                val fav = Favourite(mealId)
                viewModel.deleteFavorite(fav)
            }else{ //Tıklanan yemek favorilerde yoksa eklenir.
                val fav = Favourite(mealId)
                viewModel.saveFavorite(fav)
            }
        }
    }
}