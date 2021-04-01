package dev.chu.rv_checkbox_selection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chu.rv_checkbox_selection.databinding.ActivityMainBinding

/**
 * https://github.com/sharonelev/checkboxSelections
 */
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val list = listOf(RowModel(RowType.TopHeader, "", "" , false),
            RowModel(RowType.CatHeader, "", "Vegetables" , false),
            RowModel(RowType.ProductRow, "Tomato", "Vegetables" , false),
            RowModel(RowType.ProductRow, "Cucumber", "Vegetables" , false),
            RowModel(RowType.ProductRow, "Pepper", "Vegetables" , false),
            RowModel(RowType.CatHeader, "", "Fruit" , false),
            RowModel(RowType.ProductRow, "Apple", "Fruit" , false),
            RowModel(RowType.ProductRow, "Banana", "Fruit" , false),

            RowModel(RowType.CatHeader, "", "Vegetables2" , false),
            RowModel(RowType.ProductRow, "Tomato", "Vegetables2" , false),
            RowModel(RowType.ProductRow, "Cucumber", "Vegetables2" , false),
            RowModel(RowType.ProductRow, "Pepper", "Vegetables2" , false),
            RowModel(RowType.CatHeader, "", "Fruit2" , false),
            RowModel(RowType.ProductRow, "Apple", "Fruit2" , false),
            RowModel(RowType.ProductRow, "Banana", "Fruit2" , false),

            RowModel(RowType.CatHeader, "", "Vegetables3" , false),
            RowModel(RowType.ProductRow, "Tomato", "Vegetables3" , false),
            RowModel(RowType.ProductRow, "Cucumber", "Vegetables3" , false),
            RowModel(RowType.ProductRow, "Pepper", "Vegetables3" , false),
            RowModel(RowType.CatHeader, "", "Fruit3" , false),
            RowModel(RowType.ProductRow, "Apple", "Fruit3" , false),
            RowModel(RowType.ProductRow, "Banana", "Fruit3" , false)
        )

        val adapter = CheckBoxAdapter(this, list)
        binding.recyclerView.adapter = adapter
    }
}