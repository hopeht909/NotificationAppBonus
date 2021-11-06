package com.example.notificationappbonus

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button

class MainActivity : AppCompatActivity() {

    // declaring variables
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val notificationId = 1234
    private val channelId = "myapp.notifications"
    private val description = "Notification App Example"
    private val title = "Egg Cooking Counter"
    private val text = "Ready"
    private lateinit var btnNotification : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNotification = findViewById(R.id.btnNotification)
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        btnNotification.setOnClickListener{

            //creating intent of the next activity
            val intent = Intent(this, MainActivity2::class.java)

            //creating a pending intent of the intent we created before, in case the user clicked on the notification
            //the pending intent will be waiting until the user clicks on the notification.
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            //creating a notification channel for the notification.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(notificationChannel)

                //building the notification
                builder = Notification.Builder(this, channelId)
                    .setSmallIcon(R.drawable.ic_notify)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_notify))
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText(text)
            } else {

                // building the notification
                builder = Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_notify)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_notify))
                    .setContentIntent(pendingIntent)
                    .setContentTitle(title)
                    .setContentText(text)
            }
            val countDownTimer = object : CountDownTimer(5000,500) {


                override fun onTick(p0: Long) {

                }

                override fun onFinish() {
                    //executing the notification
                    notificationManager.notify(notificationId,builder.build())
                }


            }
            countDownTimer.start()





        }
    }
}