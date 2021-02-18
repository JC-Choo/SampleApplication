package dev.chu.deeplinking

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dev.chu.deeplinking.databinding.ActivityMainBinding

/**
 * Deep linking 이란 ?
 * 딥링크는 사용자가 웹과 애플리케이션 사이를 탐색하는 데 도움이 되는 개념이자,
 * 기본적으로 사용자를 애플리케이션의 특정 컨텐츠로 직접적으로 이동하는 URL 이다.
 *
 * Deep Link 를 어떻게 만들까 ?
 * 사용자가 URL 을 클릭했을 때, Android 시스템은 다음에 오는 작업을 순서대로, 요청이 성공할때까지 시도해야 한다.
 * 1. URI 를 처리할 수 있는 사용자가 선호하는 앱을 연다. (지정된 경우)
 * 2. URL 를 처리할 수 있는 유일한 앱을 연다.
 * 3. 사용자가 다이얼로그로부터 앱을 선택할 수 있도록 허용한다.
 *
 * intent filters 를 추가한다.
 *
 */
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val action: String? = intent?.action
        val data: Uri? = intent?.data

        Log.i("TAG", "action = $action")
        Log.i("TAG", "data = $data")
    }
}