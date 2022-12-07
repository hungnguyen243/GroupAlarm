package com.example.groupalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.groupalarm.adapter.AlarmAdapter
import com.example.groupalarm.data.Alarm
import com.example.groupalarm.databinding.ActivityScrollingBinding
import com.example.groupalarm.dialog.AlarmDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*

class ScrollingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrollingBinding
    lateinit var timePicker: TimePicker
    lateinit var pendingIntent: PendingIntent
    lateinit var alarmManager: AlarmManager

    companion object {
        const val COLLECTION_ALARMS = "alarms"
        const val ALARM_REQUEST_CODE = "alarmRequestCode"
    }
    lateinit var alarmDb: CollectionReference
    lateinit var listener: ListenerRegistration


    private lateinit var adapter: AlarmAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = AlarmAdapter(this,
            FirebaseAuth.getInstance().currentUser!!.uid
        )
        binding.recyclerPosts.adapter = adapter


        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.fab.setOnClickListener {
            val itemDialog = AlarmDialog()
            itemDialog.show(supportFragmentManager, "Add an Alarm")
        }

        alarmManager = applicationContext.getSystemService(ALARM_SERVICE) as AlarmManager

        alarmDb = FirebaseFirestore.getInstance().collection(COLLECTION_ALARMS)
        getAllAlarms()

        binding.btnRefreshAlarms.setOnClickListener {
            getAllAlarms()
        }
    }

    private fun getAllAlarms() {


        val eventListener = object : EventListener<QuerySnapshot> {
            override fun onEvent(querySnapshot: QuerySnapshot?,
                                 e: FirebaseFirestoreException?) {
                if (e != null) {
                    Toast.makeText(
                        this@ScrollingActivity, "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (docChange in querySnapshot?.getDocumentChanges()!!) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val alarm = docChange.document.toObject(Alarm::class.java)
                        val intent = Intent(applicationContext, AlarmReceiver::class.java)

                        if (Date(alarm.time) < Calendar.getInstance().time) {
                            return
                        }

                        intent.putExtra(ALARM_REQUEST_CODE, alarm.time.toInt())
                        pendingIntent = PendingIntent.getBroadcast(applicationContext, alarm.time.toInt(), intent, FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT)
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.time, pendingIntent);

                        adapter.addAlarm(alarm, docChange.document.id)

                    } else if (docChange.type == DocumentChange.Type.REMOVED) {

                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {

                    }
                }
            }
        }


        listener = alarmDb.addSnapshotListener(eventListener)

//        alarmDb.get().addOnSuccessListener {
//            documents ->
//            if (e != null) {
//                Log.w(TAG, "Listen failed.", e)
//                return@addSnapshotListener
//            }
//            System.out.println("ADDED new alarm")
//            var alarms = documents!!.map { doc -> doc.toObject(Alarm::class.java) }
//            System.out.println("SIZE ALARM " + alarms.size)
//            for (alarm in alarms) {
//                val intent = Intent(this, AlarmReceiver::class.java)
//
//                // we call broadcast using pendingIntent
//
//                // we call broadcast using pendingIntent
//                pendingIntent = PendingIntent.getBroadcast(this, 0, intent, FLAG_MUTABLE)
//
//                System.out.println("TIME set up" + Date(alarm.time).toString())
//                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarm.time, 10000, pendingIntent);
//            }
//        }


    }

    override fun onDestroy() {
        super.onDestroy()
        listener.remove()
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

    fun queryPosts() {
        val queryPosts = FirebaseFirestore.getInstance().collection(
            COLLECTION_ALARMS
        )

        val eventListener = object : EventListener<QuerySnapshot> {
            override fun onEvent(
                querySnapshot: QuerySnapshot?,
                e: FirebaseFirestoreException?
            ) {
                if (e != null) {
                    Toast.makeText(
                        this@ScrollingActivity, "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    return
                }

                for (docChange in querySnapshot?.getDocumentChanges()!!) {
                    if (docChange.type == DocumentChange.Type.ADDED) {
                        val post = docChange.document.toObject(Alarm::class.java)
                        adapter.addAlarm(post, docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.REMOVED) {
                        adapter.removePostByKey(docChange.document.id)
                    } else if (docChange.type == DocumentChange.Type.MODIFIED) {

                    }
                }
            }
        }
        queryPosts.addSnapshotListener(eventListener)
    }
}