package com.example.groupalarm.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.groupalarm.RegisterFragment
import com.example.groupalarm.RegisterFragment.Companion.COLLECTION_USERS
import com.example.groupalarm.ScrollingActivity
import com.example.groupalarm.data.User
import com.example.groupalarm.databinding.AlarmPermissionDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


class AlarmPermissionDialog(docChange: String) : DialogFragment() {
    val thisDocChange = docChange

    public lateinit var dialogViewBinding: AlarmPermissionDialogBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogViewBinding = AlarmPermissionDialogBinding.inflate(
            requireActivity().layoutInflater
        )
        dialogBuilder.setView(dialogViewBinding.root)

        dialogBuilder.setTitle("Would you like to accept this alarm for Blank_Time?")

        dialogBuilder.setPositiveButton("Accept") {
                dialog, which ->
            onResume()

        }
        dialogBuilder.setNegativeButton("Decline") {
                dialog, which ->
        }


        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()
        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
        positiveButton.setOnClickListener {
            FirebaseFirestore.getInstance().collection(RegisterFragment.COLLECTION_USERS)
                .document(userEmail).get().
                addOnSuccessListener { documentSnapshot ->
                    val user = documentSnapshot.toObject(User::class.java)
                    editUserList(thisDocChange, user!!, true)
                }
            System.out.println("ACCEPTED invite")
            dialog.dismiss()
        }
    }

    fun editUserList(key: String, user: User, addingUser: Boolean) {
        val docToUpdate = FirebaseFirestore.getInstance().collection(
            ScrollingActivity.COLLECTION_ALARMS)
            .document(key)
        if (addingUser) {
            docToUpdate
                .update(
                    "users", FieldValue.arrayUnion(user)
                )
        }
        else {
            docToUpdate.update("users", FieldValue.arrayRemove(user))
        }
    }
}