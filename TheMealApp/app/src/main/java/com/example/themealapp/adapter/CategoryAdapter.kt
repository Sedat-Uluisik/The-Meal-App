package com.example.themealapp.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.themealapp.R
import com.example.themealapp.fragment.HomeFragmentDirections
import com.example.themealapp.model.category.CategoryX

class CategoryAdapter(private val fragment: Fragment, private val list: ArrayList<CategoryX>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = View.inflate(fragment.context, R.layout.layout_category_item, null)

        val image: ImageView = view.findViewById(R.id.imageCategory)
        val categoryName: TextView = view.findViewById(R.id.category_name)

        val category = list[position]

        Glide.with(fragment).load(category.strCategoryThumb).into(image)
        categoryName.text = category.strCategory

        view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFoodListFragment("0",category.strCategory)
            Navigation.findNavController(it).navigate(action)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }

    fun refreshList(list_: List<CategoryX>){
        list.clear()
        list.addAll(list_)
        notifyDataSetChanged()
    }
}
