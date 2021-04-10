package com.example.themealapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.themealapp.R
import com.example.themealapp.clicklistener.ClickListener
import com.example.themealapp.databinding.LayoutRegionItemBinding
import com.example.themealapp.fragment.HomeFragmentDirections
import com.example.themealapp.model.region.Meal

class RegionAdapter(private val list: ArrayList<Meal>): RecyclerView.Adapter<RegionAdapter.Holder>(), ClickListener{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionAdapter.Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<LayoutRegionItemBinding>(inflater, R.layout.layout_region_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RegionAdapter.Holder, position: Int) {
        val region = list[position]

        holder.item.region = region
        holder.item.clickListener = this

    }

    class Holder(var item: LayoutRegionItemBinding): RecyclerView.ViewHolder(item.root){

    }

    fun refreshList(list_: List<Meal>){
        list.clear()
        list.addAll(list_)
        notifyDataSetChanged()
    }

    //Bölge item'ine tıklama işlemi.
    override fun onRegionClick(view: View, area: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToFoodListFragment(area, "0")
        Navigation.findNavController(view).navigate(action)
    }

}