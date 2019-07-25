package com.iffy.fikhustaz.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.picasso.Target
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.app.NotificationManager
import android.app.NotificationChannel
import android.annotation.SuppressLint
import android.app.Notification
import android.os.Build
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log.d
import androidx.core.app.NotificationCompat
import com.google.firebase.iid.FirebaseInstanceId
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.util.NotificationHelper
import com.iffy.fikhustaz.views.activity.chat.ChatActivity
import java.lang.NullPointerException


class MyFirebaseMessagingService : FirebaseMessagingService() {

    var target: Target = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            sendNotification(bitmap)
        }

        override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {

        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable) {

        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body

            NotificationHelper.displayNotification(applicationContext, title!!, body!!)
        }
    }

    private fun sendNotification(bitmap: Bitmap) {


        val style = NotificationCompat.BigPictureStyle()
        style.bigPicture(bitmap)

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val intent = Intent(applicationContext, ChatActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "101"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") val notificationChannel =
                NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX)

            //Configure Notification Channel
            notificationChannel.description = "Game Notifications"
            notificationChannel.enableLights(true)
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)

            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_app_fikh)
            .setContentTitle(Config.title)
            .setAutoCancel(true)
            .setSound(defaultSound)
            .setContentText(Config.content)
            .setContentIntent(pendingIntent)
            .setStyle(style)
            .setLargeIcon(bitmap)
            .setWhen(System.currentTimeMillis())


        notificationManager.notify(1, notificationBuilder.build())


    }

    private fun getImage(remoteMessage: RemoteMessage) {

        val data = remoteMessage.data
        Config.title = data["title"]!!
        Config.content = data["content"]!!
        Config.imageUrl = data["imageUrl"]!!
        Config.gameUrl = data["gameUrl"]!!
        //Create thread to fetch image from notification
        if (remoteMessage.data != null) {

            val uiHandler = Handler(Looper.getMainLooper())
            uiHandler.post(Runnable {
                // Get image from data Notification
                Picasso.get()
                    .load(Config.imageUrl)
                    .into(target)
            })
        }
    }
}