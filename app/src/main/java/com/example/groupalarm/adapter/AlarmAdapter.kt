package com.example.groupalarm.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groupalarm.DetailsActivity
import com.example.groupalarm.ScrollingActivity
import com.example.groupalarm.data.Alarm
import com.example.groupalarm.databinding.PostRowBinding
import com.google.firebase.firestore.FirebaseFirestore


class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    lateinit var context: Context
    lateinit var currentUid: String
    var  alarmList = mutableListOf<Alarm>()
    var  alarmKeys = mutableListOf<String>()

    constructor(context: Context, uid: String) : super() {
        this.context = context
        this.currentUid = uid
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PostRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var post = alarmList.get(holder.adapterPosition)
        holder.bind(post)
    }

    fun addAlarm(alarm: Alarm, key: String) {
        alarmList.add(alarm)
        alarmKeys.add(key)
        notifyDataSetChanged()
        notifyItemInserted(alarmList.lastIndex)
    }

    // when I remove the post object
    private fun removePost(index: Int) {
        FirebaseFirestore.getInstance().collection(
            ScrollingActivity.COLLECTION_ALARMS).document(
            alarmKeys[index]
        ).delete()

        alarmList.removeAt(index)
        alarmKeys.removeAt(index)
        notifyItemRemoved(index)
    }

    // when somebody else removes an object
    fun removePostByKey(key: String) {
        val index = alarmKeys.indexOf(key)
        if (index != -1) {
            alarmList.removeAt(index)
            alarmKeys.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(val binding: PostRowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(alarm: Alarm) {
            binding.alarmTitle.text = alarm.title.toString()
            binding.alarmAuthor.text = alarm.owner.toString()
            binding.alarmBody.text = alarm.time.toString()

//            if (currentUid == alarm.uid) {
//                binding.btnDelete.visibility = View.VISIBLE
//            } else {
//                binding.btnDelete.visibility = View.GONE
//            }

//            binding.btnDelete.setOnClickListener {
//                FirebaseFirestore.getInstance().collection(
//                    CreatePostActivity.COLLECTION_POSTS
//                ).document(
//                    postKeys[adapterPosition]
//                ).delete()
//            }

            binding.btnDetails.setOnClickListener {
                val intentDetails = Intent()
                intentDetails.setClass(
                    (context as ScrollingActivity), DetailsActivity::class.java
                )
                intentDetails.putExtra(
                    "AlarmTitle", alarm.title
                )
                intentDetails.putExtra(
                    "AlarmTime", alarm.time
                )
                intentDetails.putExtra(
                    "AlarmOwner", alarm.owner
                )

                val bundle = Bundle()
                intentDetails.putExtra(
                    "AlarmUserList", alarm.users
                )

                (context as ScrollingActivity).startActivity(Intent(intentDetails))
            }

//            if (post.imgUrl != "") {
//                binding.ivPhoto.visibility = View.VISIBLE
//
//                Glide.with(context).load(post.imgUrl).into(
//                    binding.ivPhoto)
//            } else {
//                binding.ivPhoto.visibility = View.GONE
//            }

        }
    }

}