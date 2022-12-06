package com.example.groupalarm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groupalarm.databinding.PostRowBinding
import com.google.firebase.firestore.FirebaseFirestore
//import hu.bme.aut.aitforumdemo.CreatePostActivity
import com.example.groupalarm.data.Alarm

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
//        alarmList.add(key)
        //notifyDataSetChanged()
        notifyItemInserted(alarmList.lastIndex)
    }

    // when I remove the post object
    private fun removePost(index: Int) {
//        FirebaseFirestore.getInstance().collection(
//            CreatePostActivity.COLLECTION_POSTS).document(
//            postKeys[index]
//        ).delete()

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
            binding.tvAuthor.text = alarm.isActive.toString()
            binding.tvTitle.text = alarm.users.toString()

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