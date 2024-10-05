package com.example.myforegroundservices

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStartService = findViewById<Button>(R.id.buttonStartService)
        val buttonStop = findViewById<Button>(R.id.buttonStop)

        buttonStartService.setOnClickListener {
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            }
        }

        buttonStop.setOnClickListener {
            val serviceIntent = Intent(this, MyForegroundService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                stopService(serviceIntent)
            }
        }
    }
}