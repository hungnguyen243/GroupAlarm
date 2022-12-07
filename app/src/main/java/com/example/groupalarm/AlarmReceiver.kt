package com.example.groupalarm

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.media.RingtoneManager
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.groupalarm.ScrollingActivity.Companion.ALARM_REQUEST_CODE


class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // we will use vibrator first
        // we will use vibrator first

        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show()

        var alarmUri: Uri? = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }


        // setting default ringtone

        // setting default ringtone
        val ringtone = RingtoneManager.getRingtone(context, alarmUri)

        // play ringtone

        // play ringtone
        ringtone.play()

        // Open the Stop Alarm Screen
        val alarmRequestCode = intent.getIntExtra(ALARM_REQUEST_CODE, 0)
        val newIntent = Intent(context, StopAlarmActivity::class.java)
        newIntent.putExtra("alarmRequestCode", alarmRequestCode)
        newIntent.setFlags(FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(newIntent)

    }

}