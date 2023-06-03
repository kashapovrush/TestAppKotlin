package com.kashapovrush.godrivekotlin.activities.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrivekotlin.activities.MainActivity
import com.kashapovrush.godrivekotlin.databinding.ActivitySignInBinding
import com.kashapovrush.godrivekotlin.databinding.ActivitySignInEnterCodeBinding
import com.kashapovrush.godrivekotlin.models.User
import com.kashapovrush.godrivekotlin.utilities.Constants
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrivekotlin.utilities.PreferenceManager

class SignInEnterCodeActivity() : AppCompatActivity() {

    private lateinit var binding: ActivitySignInEnterCodeBinding
    private var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var preferenceManager: PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInEnterCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(applicationContext)


        binding.password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if (binding.password.text.toString().length == 6) {
                    val id = preferenceManager.getString("SignInId")
                    val credential =
                        PhoneAuthProvider.getCredential(
                            id.toString(),
                            binding.password.text.toString()
                        )
                    auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid.toString()
                            val dataMap = mutableMapOf<String, Any>()
                            dataMap[Constants.KEY_CHILD_ID] = uid


                            database.child(KEY_COLLECTION_USERS).child(uid)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        if (!snapshot.hasChild(Constants.KEY_CHILD_USERNAME)) {
                                            dataMap[Constants.KEY_CHILD_USERNAME] = uid
                                        }
                                        database.child(KEY_COLLECTION_USERS).child(uid)
                                            .updateChildren(dataMap)
                                            .addOnCompleteListener {
                                                if (it.isSuccessful) {
//                                                    database.child(KEY_COLLECTION_USERS).child(uid).setValue(
//                                                        User(
//                                                        "", "", "", "", "", "",
//                                                            "", ""
//                                                    )
//                                                    )
                                                    val intent =
                                                        Intent(
                                                            this@SignInEnterCodeActivity,
                                                            MainActivity::class.java
                                                        )
                                                    startActivity(intent)
                                                } else {

                                                }
                                            }

                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }
                                })
                        } else {

                        }
                    }
                        .addOnFailureListener {

                        }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


    override fun onBackPressed() {

    }
}