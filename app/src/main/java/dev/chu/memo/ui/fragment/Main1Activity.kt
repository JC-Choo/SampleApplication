//package dev.chu.memo.ui.fragment
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentFactory
//import dev.chu.memo.R
//import dev.chu.memo.ui.bottom.NotificationFragment
//
//// https://proandroiddev.com/fragment-and-constructor-dependencies-3a4e6a0be152
//
//class MainActivity : AppCompatActivity() {
////    private val mainFactory = MainFragmentFactory("Now this won't crash")
////    private lateinit var mainFragment: MainFragment
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        supportFragmentManager.factory = mainFactory // This has to be done before super call
////        super.onCreate(savedInstanceState)
////
////        mainFragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, MainFragment::class.java.name)
////
////        supportFragmentManager.beginTransaction()
////            .add(R.layout.fragmentContainerView, mainFragment)
////            .commit()
////    }
//
//    private val mainFragment by fragmentFromFactory<MainFragment>()
//    override fun onCreate(savedInstanceState: Bundle?) {
//        setupKoinFactory() // This has to be done before super call
//        super.onCreate(savedInstanceState)
//
//        supportFragmentManager.beginTransaction()
//            .add(R.layout.fragmentContainerView, mainFragment)
//            .commit()
//    }
//}
//
//class MainFragmentFactory(val textToDisplay: String): FragmentFactory() {
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
//        return when(className) {
//            NotificationFragment::class.java.name -> MainFragment(textToDisplay)
//            else -> super.instantiate(classLoader, className)
//        }
//    }
//}
//
//class MainFragment(val textToDisplay: String): Fragment() {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//    }
//    // Same as earlier, no changes
//}
//
//inline fun <reified T: Fragment> AppCompatActivity.fragmentFromFactory(): Lazy<T> {
//    return lazy {
//        supportFragmentManager.fragmentFactory.instantiate(classLoader, T::class.java.name) as T
//    }
//}