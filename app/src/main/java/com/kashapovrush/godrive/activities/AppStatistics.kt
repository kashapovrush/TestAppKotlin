package com.kashapovrush.godrive.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kashapovrush.godrive.databinding.ActivityAppStatisticsBinding

class AppStatistics : AppCompatActivity() {

    private lateinit var binding: ActivityAppStatisticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}