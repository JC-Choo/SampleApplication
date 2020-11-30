//package dev.chu.memo.ui.fragment
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import dev.chu.memo.R
//
//class Main2Activity : AppCompatActivity() {
////    val bundle = Bundle().apply {
////        putString(Main2Fragment.KEY_TEXT, "Now this won't crash")
////    }
////    val mainFragment = Main2Fragment().apply {
////        arguments = bundle
////    }
//
//    val mainFragment = Main2Fragment().withArgs {
//        putString(Main2Fragment.KEY_TEXT, "Now this won't crash")
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // the usual stuff
//        supportFragmentManager.beginTransaction()
//            .add(R.layout.fragmentContainerView, mainFragment)
//            .commit()
//    }
//}
//
//class Main2Fragment: Fragment() {
////    lateinit var textToDisplay: String
//
//    private val textToDisplay by argument<String>(KEY_TEXT)
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
////        textToDisplay = requireArguments().getString(KEY_TEXT).toString()
//        // Logic related to using $textToDisplay
//    }
//    companion object {
//        const val KEY_TEXT = "KeyText"
//    }
//}
//
//inline fun <reified T : Any?> Fragment.argument(key: String): Lazy<T> {
//    return lazy(LazyThreadSafetyMode.NONE) {
//        requireArguments().get(key) as T
//    }
//}
//
//inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T {
//    return this.apply {
//        arguments = Bundle().apply(argsBuilder)
//    }
//}