package com.example.groupalarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.groupalarm.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentTitle = intent.getStringExtra("AlarmTitle")
        val intentTime = intent.getStringExtra("AlarmTime")
        val intentOwner = intent.getStringExtra("AlarmOwner")
        val intentUserList = intent.getStringExtra("AlarmUserList")


        binding.alarmTitle.text = intentTitle.toString()
        binding.alarmTime.text = intentTime.toString()
        binding.alarmOwner.text = intentOwner.toString()
        binding.alarmUserList.text = intentUserList.toString()


        binding.backBtn.setOnClickListener {
            finish()
        }

    }


}