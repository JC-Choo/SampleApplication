package dev.chu.memo.ui.handling_retrofit_result

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dev.chu.memo.R
import dev.chu.memo.base.BaseActivity
import dev.chu.memo.databinding.ActivityHandlingBinding
import dev.chu.memo.etc.extension.showToast
import dev.chu.memo.ui.rv_coroutine.UserAdapter

// https://medium.com/swlh/kotlin-sealed-class-for-success-and-error-handling-d3054bef0d4e

class HandlingActivity : BaseActivity<ActivityHandlingBinding>() {

    // `viewModels` is a extension from fragment-ktx
    private val viewModel: HandlingViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.activity_handling

    override fun initView(savedInstanceState: Bundle?) {
        // just a normal ListAdapter of RecyclerView
        val adapter = UserAdapter()

        // set our adapter to the view
        binding.recyclerView.adapter = adapter

        // observe result from the ViewModel
        viewModel.listUser.observe(this, Observer { result ->
            when (result) {
                // there is no need for type-casting, the compiler already knows that
                is ResultOf.Success -> {
//                    adapter.submitList(result)
                }
                // here as well
                is ResultOf.Failure -> {
                    showToast(result.message ?: "Unknown error message")
                }
            }

//            result.doIfSuccess { items ->
////                adapter.submit(items)
//            }
//
//            result.doIfFailure { error, throwable ->
//                showToast(error ?: "Unknown error message")
//            }

//            result.map {
//                it.items.first()
//            }.doIfSuccess { item ->
////                addToSomeWhere(item)
//            }

//            val (items) = result.withDefault {
////                postFromDB()
//            }
//            adapter.submitList(items)
        })

        viewModel.fetchUsersFromApi()
    }
}