package com.coderz.f1.notificationtest

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CounterNotificationService(
    private val context: Context
) {


    fun showNotification(counter: Int) {
        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingActivityIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, CounterNotificationReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.ic_alert)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("${context.getString(R.string.notification_content_text)} $counter")
            .setContentIntent(pendingActivityIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(
                R.drawable.ic_alert,
                context.getString(R.string.notification_action_button_text),
                incrementIntent
            )
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(1, notification)
        }
    }


}