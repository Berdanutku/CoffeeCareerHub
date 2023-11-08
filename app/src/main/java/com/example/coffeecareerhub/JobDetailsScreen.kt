package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeecareerhub.databinding.ActivityJobDetailsScreenBinding
import com.google.firebase.database.FirebaseDatabase

class JobDetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityJobDetailsScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityJobDetailsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id=intent.getStringExtra("id")

        binding.JobDetailsSaveButton.setOnClickListener {
            FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Jobdetails").child("Age").setValue(binding.JobDetailsAge.text.toString())
            FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Jobdetails").child("Phone").setValue(binding.JobDetailsPhone.text.toString())
            FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Jobdetails").child("Duration").setValue(binding.JobDetailsDuration.text.toString())
            FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Jobdetails").child("Previous").setValue(binding.JobDetailsPrevious.text.toString())
            intent= Intent(applicationContext,HomeScreen::class.java)
            startActivity(intent)
        }
        binding.JobDetailsBackButton.setOnClickListener {
            finish()
        }
    }
}