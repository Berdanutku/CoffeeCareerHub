package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.coffeecareerhub.databinding.ActivityChangeProfileScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ChangeProfileScreen : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChangeProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var id=FirebaseAuth.getInstance().currentUser?.uid.toString()
        val genders = resources.getStringArray(R.array.Gender)
        if (binding.changeGender != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
            binding.changeGender.adapter = adapter
        }

       binding.changeSaveButton.setOnClickListener {
           if (binding.changeUsername.text.isNotEmpty()) {
               var newUsername = binding.changeUsername.text.toString()
               val selected = binding.changeGender.selectedItem.toString()

               var id = FirebaseAuth.getInstance().currentUser?.uid
               FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("username")
                   .setValue(newUsername)
               FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("gender")
                   .setValue(selected)

               intent = Intent(applicationContext, HomeScreen::class.java)
               startActivity(intent)
           }
           else{
               Toast.makeText(this,"Username cannot be blank!",Toast.LENGTH_LONG).show()
           }

       }
        binding.changeBackButton.setOnClickListener {
            finish()
        }
    }
}