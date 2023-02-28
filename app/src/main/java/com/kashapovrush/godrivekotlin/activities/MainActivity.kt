package com.kashapovrush.godrivekotlin.activities

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrivekotlin.R
import com.kashapovrush.godrivekotlin.adapter.ChatAdapter
import com.kashapovrush.godrivekotlin.databinding.ActivityMainBinding
import com.kashapovrush.godrivekotlin.models.User
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_PREFERENCE_NAME
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: ChatAdapter
    private lateinit var user: User
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setUpActionBar()
        val database = Firebase.database
        reference = FirebaseDatabase.getInstance().reference
        user = User()
        val myRef = database.getReference(KEY_PREFERENCE_NAME)
        initUser()
        binding.layoutSend.setOnClickListener {
            if (binding.inputMessage.text.toString() != "") {
                myRef.child(myRef.push().key ?: "null").setValue(
                    User(
                        user.username,
                        binding.inputMessage.text.toString()
                    )
                )
            }
        }
        onChangeListener(myRef)
        initRCView()

    }

    private fun initUser() {
        val uid = auth.currentUser?.uid.toString()
        reference.child(KEY_COLLECTION_USERS).child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java) ?: User()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun initRCView() = with(binding) {
        adapter = ChatAdapter()
        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
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
        if (item.itemId == R.id.data_user) {
            val intent = Intent(this, UserDataActivity::class.java)
            startActivity(intent)
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

    private fun toastShow(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}