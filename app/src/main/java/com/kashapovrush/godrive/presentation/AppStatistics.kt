package com.kashapovrush.godrive.presentation

import android.content.Context
import android.content.Intent
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

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, AppStatistics::class.java)
        }
    }
}