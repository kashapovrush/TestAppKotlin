package com.kashapovrush.godrive.activities.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrive.activities.FreeChangeActivity
import com.kashapovrush.godrive.activities.UserDataActivity
import com.kashapovrush.godrive.adapter.ChatAdapter
import com.kashapovrush.godrive.databinding.ActivityFreeChatBinding
import com.kashapovrush.godrive.models.User
import com.kashapovrush.godrive.utilities.Constants
import com.kashapovrush.godrive.utilities.PreferenceManager
import com.kashapovrush.godrive.utilities.ViewFactory

class FreeChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFreeChatBinding
    private lateinit var adapter: ChatAdapter
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var database: DatabaseReference
    private lateinit var refMessages: DatabaseReference
    private lateinit var messagesListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFreeChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)
        database = FirebaseDatabase.getInstance().reference
        refMessages = FirebaseDatabase.getInstance().reference
    }

    override fun onStart() {
        super.onStart()
        onClickListener()
    }

    override fun onResume() {
        super.onResume()
        initRCView()
        if (preferenceManager.getString(Constants.KEY_PREFERENCE_NAME) == null) {
            binding.freeTextCity.text = "Выберите город"
        } else {
            binding.freeTextCity.text = preferenceManager.getString(Constants.KEY_PREFERENCE_NAME)
        }
    }


    private fun initRCView() {
        adapter = ChatAdapter()
        binding.freeChatRecyclerView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this@FreeChatActivity)
        binding.freeChatRecyclerView.layoutManager = linearLayoutManager
        val cityValue = preferenceManager.getString(Constants.KEY_PREFERENCE_NAME)
        refMessages =
            database.child(Constants.KEY_PREFERENCE_NAME).child(cityValue.toString())
        messagesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot: DataSnapshot in snapshot.children) {
                    val message = userSnapshot.getValue(User::class.java) ?: User()
                    adapter.setList(ViewFactory.getType(message)) {
                        binding.freeChatRecyclerView.smoothScrollToPosition(adapter.itemCount)

                    }
                }

            }


            override fun onCancelled(error: DatabaseError) {
            }
        }
        refMessages.addValueEventListener(messagesListener)

    }

    private fun onClickListener() {
        binding.freeTextCity.setOnClickListener {
            val intent = Intent(this@FreeChatActivity, FreeChangeActivity::class.java)
            startActivity(intent)
        }

        binding.freeButtonBack.setOnClickListener {
            val intent = Intent(this@FreeChatActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}