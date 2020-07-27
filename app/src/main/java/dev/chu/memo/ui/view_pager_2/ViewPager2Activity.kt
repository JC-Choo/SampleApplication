package dev.chu.memo.ui.view_pager_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chu.memo.R
import kotlinx.android.synthetic.main.activity_view_pager_2.*

class ViewPager2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_2)

        with(viewPager) {
//            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.spacing_675)
//            val pagerWidth = resources.getDimensionPixelOffset(R.dimen.spacing_375)
//            val screenWidth = resources.displayMetrics.widthPixels
//            val offsetPx = screenWidth - pageMarginPx - pagerWidth
//
//            Log.i(TAG, "offsetPx = $offsetPx, screenWidth = $screenWidth, pageMarginPx = $pageMarginPx, pagerWidth = $pagerWidth")
//
//            setPageTransformer { page, position ->
//                page.translationX = position * -offsetPx
//                Log.i(TAG, "page.translationX = ${page.translationX}, position = $position, offsetPx = $offsetPx")
//            }

            val list = listOf("A", "B", "C", "D", "E", "A", "B", "C", "D", "E")
            adapter = ViewPager2Adapter(list as MutableList<String>)
        }
    }

//    private lateinit var doppelgangerNamesArray: Array<String>
//
//    //TODO:4 Define page change callback here
//    private var doppelgangerPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
//        override fun onPageSelected(position: Int) {
//            Toast.makeText(this@ViewPager2Activity, "Selected position: $position", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        // Switch to AppTheme for displaying the activity
//        setTheme(R.style.AppTheme)
//
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_pager_2)
//
//        doppelgangerNamesArray = resources.getStringArray(R.array.doppelganger_names)
//
//        //TODO:3 Wire DoppelgangerAdapter with ViewPager2 here
//        val doppelgangerAdapter = DoppelgangerAdapter(this, doppelgangerNamesArray.size)
//        doppelgangerViewPager.adapter = doppelgangerAdapter
//
//        //TODO:5 Register page change callback here
//        doppelgangerViewPager.registerOnPageChangeCallback(doppelgangerPageChangeCallback)
//
//        //TODO:7 Change ViewPager2 orientation here
////    doppelgangerViewPager.orientation = ORIENTATION_VERTICAL
//
//        //TODO:10 Connect TabLayout and ViewPager2 here
//        TabLayoutMediator(tabLayout, doppelgangerViewPager) { tab, position ->
//            //To get the first name of doppelganger celebrities
//            tab.text = doppelgangerNamesArray[position].substringBefore(' ')
//        }.attach()
//
//        //TODO:11 Force to RTL mode
////    doppelgangerViewPager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_RTL
////    tabLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        //TODO:6 Unregister page change callback here
//        doppelgangerViewPager.unregisterOnPageChangeCallback(doppelgangerPageChangeCallback)
//    }
}