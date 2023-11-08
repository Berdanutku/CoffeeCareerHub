package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.coffeecareerhub.databinding.ActivityOwnerRequestDetailScreenBinding
import com.google.firebase.database.FirebaseDatabase
import java.lang.StringBuilder

class OwnerRequestDetailScreen : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerRequestDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerRequestDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username=intent.getStringExtra("username")
        var email=intent.getStringExtra("email")
        var gender=intent.getStringExtra("gender")
        var id=intent.getStringExtra("id")
        var name=intent.getStringExtra("name")
        var statusAcceptButton=intent.getStringExtra("statusAcceptButton")

        var sb=StringBuilder()

        sb.append("Username: $username \nEmail: $email \nGender: $gender")
        binding.ownerRequestDetailText.setText(sb)

        if (statusAcceptButton=="Accepted"){
            binding.ownerRequestDetailAcceptButton.visibility=View.GONE
            binding.ownerRequestDetailRefuseButton.visibility=View.VISIBLE
        }
        else{
            binding.ownerRequestDetailRefuseButton.visibility=View.GONE
            binding.ownerRequestDetailAcceptButton.visibility=View.VISIBLE
        }


        binding.ownerRequestDetailAcceptButton.setOnClickListener {
            binding.ownerRequestDetailAcceptButton.visibility= View.GONE
            binding.ownerRequestDetailRefuseButton.visibility=View.VISIBLE
            FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("Requests").child(id.toString()).child("status").setValue("Accepted")
            FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Requests").child(name.toString()).child("status").setValue("Accepted")
        }
        binding.ownerRequestDetailRefuseButton.setOnClickListener {
            binding.ownerRequestDetailRefuseButton.visibility=View.GONE
            binding.ownerRequestDetailAcceptButton.visibility=View.VISIBLE
            FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("Requests").child(id.toString()).child("status").setValue("Refused")
            FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Requests").child(name.toString()).child("status").setValue("Refused")
        }
        binding.ownerRequestDetailBackButton.setOnClickListener {
            finish()
        }
        binding.ownerRequestDetailViewButton.setOnClickListener {
            intent= Intent(applicationContext,OwnerViewJobDetailsScreen::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
    }
}