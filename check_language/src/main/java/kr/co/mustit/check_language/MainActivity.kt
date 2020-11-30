package kr.co.mustit.check_language

import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView

// 참고 : https://medium.com/swlh/android-app-specific-language-change-programmatically-using-kotlin-d650a5392220

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar?.setTitle(R.string.app_name)

        val tv = findViewById<TextView>(R.id.test_text_view)
        tv.text = getString(R.string.my_string_name)

        val res: Resources = resources
        val pluralViewOne = findViewById<TextView>(R.id.plural_view_one)
        val quantityStringFor1: String = res.getQuantityString(R.plurals.my_cats, 1, 1)
        pluralViewOne.text = quantityStringFor1

        val pluralViewFew = findViewById<TextView>(R.id.plural_view_few)
        val quantityStringFor2: String = res.getQuantityString(R.plurals.my_cats, 2, 2)
        pluralViewFew.text = quantityStringFor2

        val pluralViewMany = findViewById<TextView>(R.id.plural_view_many)
        val quantityStringFor5: String = res.getQuantityString(R.plurals.my_cats, 5, 5)
        pluralViewMany.text = quantityStringFor5
    }
}