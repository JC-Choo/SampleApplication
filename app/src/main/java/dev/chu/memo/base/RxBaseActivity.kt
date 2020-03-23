package dev.chu.memo.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

abstract class RxBaseActivity<T: ViewDataBinding> : AppCompatActivity() {
    protected val compositeDisposable = CompositeDisposable()

    protected lateinit var binding: T
        private set

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!super.onSupportNavigateUp())
            onBackPressed()
        return true
    }
}

abstract class RxBaseViewModelActivity<T: ViewDataBinding>: RxBaseActivity<T>() {
    abstract val viewModel: RxBaseViewModel

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}