package com.kashapovrush.godrivekotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kashapovrush.godrivekotlin.databinding.ActivityNotificationSettingsBinding
import com.kashapovrush.godrivekotlin.databinding.ActivityUserDataBinding

class NotificationSettings : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationSettingsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}