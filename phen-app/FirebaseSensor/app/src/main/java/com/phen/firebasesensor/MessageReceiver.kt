package com.phen.firebasesensor

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessageReceiver : FirebaseMessagingService() {

    private val REQUEST_CODE = 1

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage?.data?.get("title")
        val message = remoteMessage?.data?.get("message")
        val id : Int = remoteMessage?.data?.get("id")?.toInt()!!

        val i = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE,
                i, PendingIntent.FLAG_UPDATE_CURRENT)

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("my_channel_01",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this,"my_channel_01" )
                    .setContentText(message)
                    .setContentTitle(title)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setSound(Uri.parse("""android.resource://$packageName/${R.raw.alert}"""))
                    .build()
        } else {
            NotificationCompat.Builder(this )
                    .setContentText(message)
                    .setContentTitle(title)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setSound(Uri.parse("""android.resource://$packageName/${R.raw.alert}"""))
                    .build()
        }

        manager.notify(id, notification)
    }
}