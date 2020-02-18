package dev.chu.memo.base

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dev.chu.memo.view.dialog.UtilDialog

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity() {

    protected lateinit var binding : T
        private set

    private var utilDialog: UtilDialog? = null

    @LayoutRes
    abstract fun getLayoutRes(): Int
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        binding.lifecycleOwner = this

        utilDialog = UtilDialog()

        initView()
    }

    fun showProgress() {
        utilDialog?.showCustomProgressDialogAboutServer(this)
        window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun hideProgress() {
        utilDialog?.closeCustomProgressDialog()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}