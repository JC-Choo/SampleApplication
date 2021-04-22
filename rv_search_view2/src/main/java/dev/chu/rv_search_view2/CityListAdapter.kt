package dev.chu.rv_search_view2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import dev.chu.rv_search_view2.databinding.ItemCityBinding
import dev.chu.rv_search_view2.dto.City
import java.util.*
import kotlin.collections.ArrayList

class CityListAdapter(
    private var cityDataList: ArrayList<City>
) : RecyclerView.Adapter<CityViewHolder>() {

    // Create a copy of localityList that is not a clone
    // (so that any changes in localityList aren't reflected in this list)
    val initialCityDataList = ArrayList<City>().apply {
        addAll(cityDataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cityDataList[position])
    }

    override fun getItemCount(): Int = cityDataList.count()

    fun getFilter(): Filter = cityFilter

    @Suppress("UNCHECKED_CAST")
    private val cityFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: ArrayList<City> = ArrayList()
            if (constraint.isNullOrEmpty()) {
                initialCityDataList.let {
                    filteredList.addAll(it)
                }
            } else {
                val query = constraint.toString().trim().toLowerCase()
                initialCityDataList.forEach {
                    if (it.cityName.toLowerCase(Locale.ROOT).contains(query)) {
                        filteredList.add(it)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results?.values is ArrayList<*>) {
                cityDataList.clear()
                cityDataList.addAll(results.values as ArrayList<City>)
                notifyDataSetChanged()
            }
        }
    }
}

class CityViewHolder(
    private val binding: ItemCityBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: City) {
        binding.item = item
        binding.executePendingBindings()
    }
}