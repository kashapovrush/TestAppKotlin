package com.kashapovrush.godrive.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.kashapovrush.godrive.activities.sign.FreeChatActivity
import com.kashapovrush.godrive.databinding.ActivityFreeChangeBinding
import com.kashapovrush.godrive.utilities.Constants
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_PREFERENCE_NAME
import com.kashapovrush.godrive.utilities.PreferenceManager

class FreeChangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFreeChangeBinding
    private lateinit var preferenceManager: PreferenceManager
    val listCity = arrayOf("Выберите город", "Туймазы", "Октябрьский", "Шаран", "Кандры", "Трасса М5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)
        val freeSelectCity = binding.freeSelectCity
        var arrayAdapter = ArrayAdapter<String>(
            this@FreeChangeActivity,
            R.layout.simple_spinner_dropdown_item,
            listCity
        )
        freeSelectCity.adapter = arrayAdapter
        freeSelectCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (listCity[position] != listCity[0]) {
                    preferenceManager.putString(KEY_PREFERENCE_NAME, listCity[position])
                    binding.freeChoiseCity.text = listCity[position]
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    override fun onStart() {
        super.onStart()
        onClickListener()
        binding.freeChoiseCity.text = preferenceManager.getString(KEY_PREFERENCE_NAME)
    }

    private fun onClickListener() {
        binding.freeButtonBack.setOnClickListener {
            val intent = Intent(this@FreeChangeActivity, FreeChatActivity::class.java)
            startActivity(intent)
        }
    }
}
