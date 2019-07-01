package com.iffy.fikhustaz.util

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.views.activity.chat.ChatActivity

object NotificationHelper {

    fun displayNotification(context: Context, title: String, body: String) {

        val intent = Intent(context, ChatActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            100,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val mBuilder = NotificationCompat.Builder(context, ChatActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_app_fikh)
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val mNotificationMgr = NotificationManagerCompat.from(context)
        mNotificationMgr.notify(1, mBuilder.build())

    }

}