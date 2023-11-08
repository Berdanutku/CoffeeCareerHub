package com.example.coffeecareerhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeecareerhub.databinding.ActivityOwnerViewJobDetailsScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class OwnerViewJobDetailsScreen : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerViewJobDetailsScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerViewJobDetailsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database=FirebaseDatabase.getInstance().reference
        var id=intent.getStringExtra("id")

        var getData=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb= StringBuilder()
                var age=snapshot.child("Users").child(id.toString()).child("Jobdetails").child("Age").getValue()
                var phone=snapshot.child("Users").child(id.toString()).child("Jobdetails").child("Phone").getValue()
                var duration=snapshot.child("Users").child(id.toString()).child("Jobdetails").child("Duration").getValue()
                var previous=snapshot.child("Users").child(id.toString()).child("Jobdetails").child("Previous").getValue()
                sb.append("Age: $age \nPhone: $phone \nDuration: $duration \nPrevious: $previous ")

               binding.ownerViewJobDetailsText.setText(sb)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getData)

        binding.ownerViewJobDetailsBackButton.setOnClickListener {
            finish()
        }
    }
}