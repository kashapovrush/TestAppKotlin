package com.kashapovrush.godrive.presentation.sign

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrive.presentation.mainChat.MainActivity
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_CHILD_ID
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrive.utilities.Constants.Companion.KEY_CHILD_USERNAME

class PhoneAuthActivity(private val signInActivity: AppCompatActivity, val view: EditText) {

    private var auth: FirebaseAuth = Firebase.auth
    private var id: String = ""
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(signInActivity, MainActivity::class.java)
                        signInActivity.startActivity(intent)
                    } else {
                        toastShow("No no")
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.e("error", p0.message.toString())
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                id = verificationId
            }
        }

    init {
        view.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if (view.text.toString().length == 6) {
                    val credential =
                        PhoneAuthProvider.getCredential(id, view.text.toString())
                    auth.signInWithCredential(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val uid = auth.currentUser?.uid.toString()
                            val dataMap = mutableMapOf<String, Any>()
                            dataMap[KEY_CHILD_ID] = uid


                            database.child(KEY_COLLECTION_USERS).child(uid)
                                .addListenerForSingleValueEvent(object :ValueEventListener{
                                    override fun onDataChange(snapshot: DataSnapshot) {

                                        if (!snapshot.hasChild(KEY_CHILD_USERNAME)) {
                                            dataMap[KEY_CHILD_USERNAME] = uid
                                        }
                                        database.child(KEY_COLLECTION_USERS).child(uid).updateChildren(dataMap)
                                            .addOnCompleteListener {
                                                if (it.isSuccessful) {
                                                    val intent =
                                                        Intent(signInActivity, MainActivity::class.java)
                                                    signInActivity.startActivity(intent)
                                                } else {
                                                    toastShow(it.exception?.message.toString())
                                                }
                                            }
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                    }
                                })
                        } else {
                            toastShow("No no")
                        }
                    }
                        .addOnFailureListener {
                            toastShow("NO")
                        }
                } else {

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun toastShow(message: String) {
        Toast.makeText(signInActivity, message, Toast.LENGTH_SHORT).show()
    }
}
