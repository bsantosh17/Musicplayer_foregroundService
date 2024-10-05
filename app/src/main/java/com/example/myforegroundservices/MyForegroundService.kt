package com.example.myforegroundservices

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class MyForegroundService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, "MY_CHANNEL_ID")
            .setContentTitle("Foreground Service")
            .setContentText("Running task in the background")
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        startForeground(1, notification)

        // Initialize MediaPlayer with audio resource
        mediaPlayer = MediaPlayer.create(this, R.raw.song) // song is the name of your audio file without extension

        mediaPlayer.start()
        Thread {
            for (i in 40 downTo 1) {
                Thread.sleep(2000)
                println("Running background task: $i")

                //Toast.makeText(applicationContext,"Running background task: $i",Toast.LENGTH_SHORT).show()
            }
            stopSelf()  // Stops service when the task is done
        }.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        // Stop the MediaPlayer when the service is destroyed
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()  // Release resources
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "MY_CHANNEL_ID",
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}
