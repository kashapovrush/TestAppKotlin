package com.kashapovrush.godrivekotlin.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.kashapovrush.godrivekotlin.databinding.ActivityUserDataBinding
import com.kashapovrush.godrivekotlin.models.User
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_COLLECTION_USERS
import com.kashapovrush.godrivekotlin.utilities.Constants.Companion.KEY_USERNAME

class UserDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDataBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveData.setOnClickListener {
            changeName()
        }
    }

    private fun changeName() {
        val uid = auth.currentUser?.uid.toString()
        val name: String = binding.inputName.text.toString()
        if (name.isEmpty()) {
            toastShow("Введите имя")
        } else {
            database.child(KEY_COLLECTION_USERS).child(uid).child(KEY_USERNAME).setValue(name)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        toastShow("Данные обновлены")
                        user.name = name
                    }
                }
        }
    }

    private fun toastShow(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}