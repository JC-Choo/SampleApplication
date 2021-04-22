package dev.chu.rv_search_view2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import dev.chu.extensions.TAG
import dev.chu.rv_search_view2.databinding.ActivityMainBinding
import dev.chu.rv_search_view2.dto.City
import dev.chu.rv_search_view2.dto.MockDataProvider

/**
 * https://medium.com/geekculture/searchable-recyclerview-e316289edc25
 */
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private var adapter: CityListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        Log.i(TAG, "onCreate")

        setUpSearchView()
        setUpRecyclerView()
    }

    private fun setUpSearchView() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter?.getFilter()?.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.getFilter()?.filter(newText)
                return true
            }
        })
    }

    private fun setUpRecyclerView() {
        //add item decoration for divider
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(this, R.drawable.line_divider)?.let { itemDecorator.setDrawable(it) }
        binding.list.addItemDecoration(itemDecorator)

        //create a copy of city list
        val cityList = MockDataProvider().getCityDataList()
        val cityListCopy = ArrayList<City>().apply {
            addAll(cityList)
        }

        //attach adapter to list
        adapter = CityListAdapter(cityListCopy)
        binding.list.adapter = adapter
    }
}