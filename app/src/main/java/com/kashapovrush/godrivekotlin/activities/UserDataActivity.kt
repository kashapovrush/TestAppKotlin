package com.kashapovrush.godrivekotlin.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kashapovrush.godrivekotlin.databinding.ActivityUserDataBinding
import com.kashapovrush.godrivekotlin.models.User
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_CHILD_USERNAME
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_COLLECTION_USERNAMES
import java.util.*

class UserDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDataBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: User
    private lateinit var newUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        user = User()
        initUser()
        binding.saveData.setOnClickListener {
            change()
        }

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.layoutImage.setOnClickListener {
            changePhotoUser()
        }

    }

    private fun changePhotoUser() {

    }

    private fun initUser() {
        val uid = auth.currentUser?.uid.toString()
        database.child(KEY_COLLECTION_USERS).child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.getValue(User::class.java) ?: User()
                    binding.inputName.setText(user.username)
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    private fun change() {
        newUsername = binding.inputName.text.toString().toLowerCase(Locale.getDefault())
        if (newUsername.isEmpty()) {
            toastShow("Введите username")
        } else {
            database.child(KEY_COLLECTION_USERNAMES)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(newUsername)) {
                            toastShow("Такой пользователь уже существует")
                        } else {
                            changeUsername()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
        }
    }

    private fun changeUsername() {
        val uid: String = auth.currentUser?.uid.toString()
        database.child(KEY_COLLECTION_USERNAMES).child(newUsername).setValue(uid)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }
    }

    private fun updateCurrentUsername() {
        val uid = auth.currentUser?.uid.toString()
        database.child(KEY_COLLECTION_USERS).child(uid).child(KEY_CHILD_USERNAME)
            .setValue(newUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    toastShow("Данные обновлены")
                    deletedOldUsername()
            } else {
                    toastShow("Error")
                }
        }
    }

    private fun deletedOldUsername() {
        database.child(KEY_COLLECTION_USERNAMES).child(user.username).removeValue()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    toastShow("Успешно")
                    user.username = newUsername
                }
            }
    }

    private fun toastShow(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}