package com.example.groupalarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.groupalarm.adapter.AuthViewPagerAdapter
import com.example.groupalarm.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myViewPagerAdapter = AuthViewPagerAdapter(this, 2)

        binding.mainViewPager.adapter = myViewPagerAdapter

        var pageNames: Array<String> = resources.getStringArray(R.array.tab_names)
        TabLayoutMediator(binding.tabLayout, binding.mainViewPager) { tab, position ->
            tab.text = pageNames[position]
        }.attach()

        //mainViewPager.setPageTransformer(ZoomOutPageTransformer())
//            mainViewPager.setPageTransformer(DepthPageTransformer())
    }
}