package dev.chu.navigationui

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dev.chu.extensions.isAndroid26More

/**
 * 알림을 포스팅(게시)하기 위한 Utility class
 * 이 클래스는 (필요할 경우)알림 채널을 만들고 요청할 때 게시한다.
 */
object Notifier {

    private const val channelId = "Default"

    fun init(activity: Activity) {
        if (isAndroid26More()) {
            val notificationManager = activity.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            val existingChannel = notificationManager.getNotificationChannel(channelId)
            if (existingChannel == null) {
                val name = activity.getString(R.string.defaultChannel)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = activity.getString(R.string.notificationDescription)
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun postNotification(id: Long, context: Context, intent: PendingIntent) {
        val notification = NotificationCompat.Builder(context, channelId).apply {
            setContentTitle(context.getString(R.string.deepLinkNotificationTitle))
            setSmallIcon(R.drawable.donut_with_sprinkles)
            setContentText(context.getString(R.string.addDonutInfo))
            priority = NotificationCompat.PRIORITY_HIGH
            setContentIntent(intent)
            setAutoCancel(true)
        }.build()

        val notificationManager = NotificationManagerCompat.from(context)
        // 이전 알림 제거; 오직 한 번에 하나씩 최신 항목을 편집할 수 있다.
        notificationManager.cancelAll()
        notificationManager.notify(id.toInt(), notification)
    }
}