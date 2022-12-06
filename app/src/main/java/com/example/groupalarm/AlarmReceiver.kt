package com.example.groupalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.widget.Toast


class AlarmReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        // we will use vibrator first
        // we will use vibrator first

        System.out.println("ALARM WAKE UP")
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
    }

}