package dev.chu.memo.ui.activity._new

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import dev.chu.memo.etc.extension.TAG
import dev.chu.memo.ui.activity.*

data class SecondResult(
    val typeString: String,
    val typeInt: Int
)

class ActivityResultSampleActivity : AppCompatActivity() {

    val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult() /* StartActivityForResult 처리를 담당 */) { activityResult ->
            Log.i(TAG, "requestActivity activityResult = $activityResult")
            toast(activityResult.prettyString)
        }

    val requestSecondVanilla = registerForActivityResult(object : ActivityResultContract<Unit, ActivityResult>() {
        override fun createIntent(context: Context, input: Unit?): Intent {
            return Intent(context, ResultSecondActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): ActivityResult {
            return ActivityResult(resultCode, intent)
        }
    }) { activityResult ->
        toast(activityResult.prettyString)
    }

    val secondCustom = registerForActivityResult(object : ActivityResultContract<Unit, SecondResult?>() {
        override fun createIntent(context: Context, input: Unit?): Intent {
            return Intent(context, ResultSecondActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): SecondResult? {
            return if(resultCode == Activity.RESULT_OK && intent != null) {
                SecondResult(
                    typeString = intent.getStringExtra(ResultSecondActivity.key_string_case),
                    typeInt = intent.getIntExtra(ResultSecondActivity.key_int_case, 0)
                )
            } else {
                null
            }
        }
    }) { secondResult ->
        if(secondResult != null) {
            toast(secondResult.toString())
        }
    }

    val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        toast("Location granted: $isGranted")
    }

    val requestLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission(), ACCESS_FINE_LOCATION
    ) { isGranted ->
        toast("Location granted: $isGranted")
    }

    val takePicturePreview = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        toast("Got picture: $bitmap")
    }

    val getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        toast("Got image: $uri")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setContentView {
            add(::LinearLayout) {
                orientation = VERTICAL
                button("Show second Activity") {
                    val intent = Intent(context, ResultSecondActivity::class.java)
                    requestActivity.launch(intent)
                }
                button("Show second Activity (Vanilla)") {
                    requestSecondVanilla.launch()
                }
                button("Show second Activity (Custom Result)") {
                    secondCustom.launch()
                }
                button("Request location permission") {
                    requestLocation.launch()
                }
                button("Request location permission (Vanilla)") {
                    requestPermission.launch(ACCESS_FINE_LOCATION)
                }
                button("Take pic") {
                    takePicturePreview.launch()
                }
                button("Pick an image") {
                    getContent.launch("image/*")
                }
                button("Show fragment Sample", color = 0xFF81D4FA.toInt()) {
                    val intent = Intent(context, ActivityResultSampleFragmentActivity::class.java)
                    requestActivity.launch(intent)
                }
                button("Go Detail Setting", color = 0xFF81D4FA.toInt()) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", packageName, null)
                    }
                    requestActivity.launch(intent)
                }
            }
        }
    }
}