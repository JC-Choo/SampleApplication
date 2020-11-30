package kr.co.mustit.check_language

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import java.util.*

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
//        // get chosen language from shared preference
//        val localeToSwitchTo = PreferenceManager(newBase).getAppLanguage()
//
//        val localeUpdateContext: ContextWrapper = ContextUtils.updateLocale(newBase, localeToSwitchTo)

        if (newBase != null) {
            val localeToSwitchTo = Locale(MainApplication.get().getPrefString("language", ""))
            val localeUpdateContext: ContextWrapper = ContextUtils.updateLocale(newBase, localeToSwitchTo)
        }

        super.attachBaseContext(newBase)
    }
}