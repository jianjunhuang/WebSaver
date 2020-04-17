package com.jianjun.websaver.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.jianjun.websaver.R


class WebService : Service() {
    private var mNM: NotificationManager? = null

    companion object {
        const val ACTION_STOP = "action_stop"
        const val ACTION_START = "action_start"
        const val CHANNEL = "WebSaver"
        const val NOTIFICATION_ID = 1001
    }


    override fun onCreate() {
        super.onCreate()
        mNM = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                createNotification()
            }
            ACTION_STOP -> {
                stopForeground(true)
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun createNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL,
                CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mNM?.createNotificationChannel(serviceChannel)
        }

        val stopIntent =
            PendingIntent.getService(
                this,
                400,
                Intent(this, WebService::class.java).apply { action = ACTION_STOP },
                0
            )

        val notification = NotificationCompat.Builder(this, CHANNEL)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setWhen(System.currentTimeMillis())
            .setContentTitle(getString(R.string.settings_share_computer))
            .addAction(R.mipmap.ic_launcher, "stop", stopIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

}