package com.kashapovrush.godrive.presentation.sign

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrive.presentation.mainChat.MainActivity
import com.kashapovrush.godrive.databinding.ActivitySignInEnterCodeBinding
import com.kashapovrush.godrive.utilities.Constants
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrive.utilities.PreferenceManager

class SignInEnterCodeActivity : AppCompatActivity() {

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
                    runProgressBar()
                    val id = preferenceManager.getString(SignInActivity.EXTRA_SIGN_IN_ID)
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
                                                    val intent =
                                                        Intent(
                                                            this@SignInEnterCodeActivity,
                                                            MainActivity::class.java
                                                        )
                                                    startActivity(intent)
                                                    finish()
                                                } else {
                                                    toastShow("Ошибка, попробуйте позже :(")
                                                    stopProgressBar()
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
                            toastShow("Вы ввели неверный код")
                            stopProgressBar()
                        }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun toastShow(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }


    override fun onBackPressed() {

    }

    fun runProgressBar() {
        binding.progressBarEnterCode.visibility = View.VISIBLE
    }

    fun stopProgressBar() {
        binding.progressBarEnterCode.visibility = View.INVISIBLE
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, SignInEnterCodeActivity::class.java)
        }
    }
}