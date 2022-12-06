package com.example.groupalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.groupalarm.data.Alarm
import com.example.groupalarm.databinding.ActivityScrollingBinding
import com.example.groupalarm.dialog.AlarmDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    lateinit var timePicker: TimePicker
    lateinit var pendingIntent: PendingIntent
    lateinit var alarmManager: AlarmManager

    companion object {
        const val COLLECTION_ALARMS = "alarms"
    }
    lateinit var alarmDb: CollectionReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.fab.setOnClickListener {
            val itemDialog = AlarmDialog()
            itemDialog.show(supportFragmentManager, "Add an Alarm")
        }

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        alarmDb = FirebaseFirestore.getInstance().collection(COLLECTION_ALARMS)
        getAllAlarms()
    }

//    override fun onResume() {
//        super.onResume()
//    }

    private fun getAllAlarms() {
        var alarms: List<Alarm> = ArrayList()
        alarmDb.get().addOnSuccessListener {
            documents->
            alarms = documents.map { doc -> doc.toObject(Alarm::class.java) }
            for (alarm in alarms) {
                // set Alarms
//                pendingIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
//                    PendingIntent.getBroadcast(this, 0, intent, FLAG_IMMUTABLE)
//                }
                val intent = Intent(this, AlarmReceiver::class.java)

                // we call broadcast using pendingIntent

                // we call broadcast using pendingIntent
                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, FLAG_MUTABLE)

                System.out.println("TIME set up" + alarm.time)
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarm.time, 10000, pendingIntent);
            }
        }.addOnFailureListener {
            System.out.println("FAILED getting alarms")
        }
    }

//    fun setAlarms() {
//        var allAlarms: List<Alarm> = getAllAlarms()
//        for (alarm in allAlarms) {
//            // set Alarms
//            pendingIntent = Intent(this, AlarmReceiver::class.java).let { intent ->
//                PendingIntent.getBroadcast(this, 0, intent, 0)
//            }
//            System.out.println("TIME set up" + alarm.time)
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.time, pendingIntent);
//        }
//    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}