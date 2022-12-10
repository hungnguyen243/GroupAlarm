package com.example.groupalarm.dialog

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.os.Build
import android.os.Bundle
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.groupalarm.R
import com.example.groupalarm.RegisterFragment
import com.example.groupalarm.ScrollingActivity.Companion.COLLECTION_ALARMS
import com.example.groupalarm.data.Alarm
import com.example.groupalarm.data.User
import com.example.groupalarm.databinding.AlarmDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.util.*
import kotlin.collections.ArrayList

class AlarmDialog: DialogFragment() {

    lateinit var pendingIntent: PendingIntent

    lateinit var dialogViewBinding: AlarmDialogBinding

    private var isEditMode = false


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())


        dialogBuilder.setTitle(getString(R.string.createAlarm))

        dialogViewBinding = AlarmDialogBinding.inflate(requireActivity().layoutInflater)
        dialogBuilder.setView(dialogViewBinding.root)

        var timePicker = dialogViewBinding.timePicker

        dialogBuilder.setPositiveButton(getString(R.string.confirm_btn)) {
                dialog, which ->
            var time: Long

            val calendar: Calendar = Calendar.getInstance()

            // calendar is called to get current time in hour and minute

            // calendar is called to get current time in hour and minute
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.hour)
            calendar.set(Calendar.MINUTE, timePicker.minute)


            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                // setting time as AM and PM
                if (Calendar.AM_PM == 0)
                    time += (1000 * 60 * 60 * 12);
                else
                    time += (1000 * 60 * 60 * 24);
            }

            val currUserEmail = FirebaseAuth.getInstance().currentUser!!.email!!
            val alarmCollections = FirebaseFirestore.getInstance().collection(COLLECTION_ALARMS)
            //get User object by email, then add new Alarm to db
            FirebaseFirestore.getInstance().collection(RegisterFragment.COLLECTION_USERS)
            .document(currUserEmail).get().
            addOnSuccessListener { documentSnapshot ->
                val user = documentSnapshot.toObject(User::class.java)
                val newAlarm = Alarm(
                    dialogViewBinding.etAlarmTitle.text.toString(),
                    time,
                    dialogViewBinding.toggleButton.isChecked,
                    arrayListOf(user!!),
                    FirebaseAuth.getInstance().currentUser!!.email!!,
                )
                alarmCollections.add(newAlarm)
            }
        }
        dialogBuilder.setNegativeButton(getString(R.string.cancel_btn)) {
                dialog, which ->
        }
        return dialogBuilder.create()
    }

//    fun onToggleClicked(view: View) {
//        var time: Long
//        if ((view as ToggleButton).isChecked) {
//            Toast.makeText(requireActivity(), "Alarm turned on",  Toast.LENGTH_SHORT).show()
//
//            // Alarm rings continuously until toggle button is turned off
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
//            // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);
//        } else {
//            alarmManager.cancel(pendingIntent);
//
//        }
//    }


}