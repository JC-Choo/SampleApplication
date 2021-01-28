package dev.chu.touchevent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import dev.chu.touchevent.databinding.ActivityMainBinding

/**
 * 참고 : https://veldan1202.medium.com/touch-event-management-c69d156fda96
 */
class MainActivity : AppCompatActivity() {

    // Binding
    private lateinit var binding: ActivityMainBinding

    // Components
    private lateinit var adapter: RecyclerAdapter

    // Components UI
    private lateinit var root: MyMotionLayout
    private lateinit var btn: Button
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstEx()
        secondEx()
    }

    private fun firstEx() {
//        binding.apply {
//            root.setOnClickListener {
//                Toast.makeText(this@MainActivity, "ROOT", Toast.LENGTH_SHORT).show()
//            }
//
//            btn.setOnClickListener {
//                Toast.makeText(this@MainActivity, "CHILD", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun secondEx() {
        initComponentUI()
        initListeners()
        initRecycler()
    }

    private fun initComponentUI() {
        binding.also {
            root = it.root
            btn = it.btn
        }
    }

    private fun initListeners() {
        btn.setOnClickListener {
            adapter.setValue("${++i}")
        }
    }

    private fun initRecycler() {
        adapter = RecyclerAdapter()
        binding.recycle.adapter = adapter
    }
}