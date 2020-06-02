package dev.chu.memo.ui.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.chu.memo.R
import dev.chu.memo.ui.notification.ui.EggTimerFragment

/**
 * /receiver
 * receiver 패키지는 AlarmReceiver and SnoozeReceiver 라 이름 붙여진 두 브로드캐스트 리시버를 포함한다.
 * AlarmReceiver 는 사용자-정의 타이머가 작동될 때 알림을 보내기 위해 AlarmManager 에 의해 트리거된다.
 * SnoozeReceiver 는 알림을 일시정지 하기 위해 사용자 클릭을 처리한다.
 *
 * /ui
 * ui 패키지는 앱의 UI 부분에 속하는 EggTimerFragment 를 포함한다.
 * EggTimerViewModel 은 타이머의 시작과 취소, 생명주기 관련 앱 작업에 대해 담당한다.
 *
 * /util
 * BindingUtils.kt 는 UI와 ViewModel 사이에 데이터 바인딩을 가능하게 하는 바인딩 어댑터를 가진다.
 * NotificationUtils.kt 는 NotificationManager 에 대한 확장함수를 가진다.
 */

// 참고 : https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-notifications/#4

class NotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, EggTimerFragment.newInstance())
                .commitNow()
        }
    }

}