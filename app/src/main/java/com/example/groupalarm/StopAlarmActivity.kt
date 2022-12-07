package com.example.groupalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.groupalarm.ScrollingActivity.Companion.ALARM_REQUEST_CODE
import com.example.groupalarm.databinding.ActivityStopAlarmBinding


class StopAlarmActivity : AppCompatActivity() {

    lateinit var binding: ActivityStopAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Stop alarm
        binding.btnStopAlarm.setOnClickListener {
            val alarmRequestCode = intent.getIntExtra(ALARM_REQUEST_CODE, 0)
            val newIntent = Intent(applicationContext, AlarmReceiver::class.java)
            newIntent.setAction("ABCDE")
            val pendingIntent = PendingIntent.getBroadcast(applicationContext, alarmRequestCode, newIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

            val alarmManager = applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            Toast.makeText(this, "caneled alarm", Toast.LENGTH_LONG).show()
        }

        // Bring user back to app
        binding.btnBackToApp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}