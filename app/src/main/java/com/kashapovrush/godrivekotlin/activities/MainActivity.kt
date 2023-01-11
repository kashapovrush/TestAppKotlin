package com.kashapovrush.godrivekotlin.activities

import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrivekotlin.R
import com.kashapovrush.godrivekotlin.adapter.ChatAdapter
import com.kashapovrush.godrivekotlin.databinding.ActivityMainBinding
import com.kashapovrush.godrivekotlin.models.User
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setUpActionBar()
        val database = Firebase.database
        val myRef = database.getReference("message")

        binding.layoutSend.setOnClickListener {
            if (binding.inputMessage.text.toString() != "") {
                myRef.child(myRef.push().key ?: "null").setValue(
                    User(
                        auth.currentUser?.displayName,
                        binding.inputMessage.text.toString()
                    )
                )
            }
        }
        onChangeListener(myRef)
        initRCView()
    }

    private fun initRCView() = with(binding) {
        adapter = ChatAdapter()
        var linearLayoutManager = LinearLayoutManager(this@MainActivity)
        linearLayoutManager.stackFromEnd = true
        chatRecyclerView.layoutManager = linearLayoutManager
        chatRecyclerView.adapter = adapter
    }

    private fun onChangeListener(dRef: DatabaseReference) {
        dRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<User>()
                for (s in snapshot.children) {
                    val user = s.getValue(User::class.java)
                    if (user != null) {
                        list.add(user)
                    }
                }
                adapter.submitList(list)
                binding.inputMessage.text = null
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sign_out) {
            auth.signOut()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpActionBar() {
        val actionBar = supportActionBar
        Thread {
            val bitmap = Picasso.get().load(auth.currentUser?.photoUrl).get()
            val drawableIcon = BitmapDrawable(resources, bitmap)
            runOnUiThread {
                actionBar?.setDisplayHomeAsUpEnabled(true)
                actionBar?.setHomeAsUpIndicator(drawableIcon)
                actionBar?.title = auth.currentUser?.displayName
            }

        }.start()
    }
}