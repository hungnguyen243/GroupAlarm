package com.example.groupalarm

import android.app.AlarmManager
import android.app.KeyguardManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.groupalarm.ScrollingActivity.Companion.ALARM_REQUEST_CODE
import com.example.groupalarm.ScrollingActivity.Companion.alarmIntents
import com.example.groupalarm.databinding.ActivityStopAlarmBinding


class StopAlarmActivity : AppCompatActivity() {

    lateinit var binding: ActivityStopAlarmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStopAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }

        // setting default ringtone
        val ringtone = RingtoneManager.getRingtone(this, alarmUri)

        // play ringtone
        ringtone.play()



        // STOP ALARM
        binding.btnStopAlarm.setOnClickListener {
            Toast.makeText(this, "Alarm turned off", Toast.LENGTH_LONG).show()
            ringtone.stop()
        }

        // Bring user back to app
        binding.btnBackToApp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}