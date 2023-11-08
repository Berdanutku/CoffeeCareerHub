package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.coffeecareerhub.databinding.ActivityHomeScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import java.lang.StringBuilder

class HomeScreen : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LoadSkeletons()
        HideSkeletons()

        var database = FirebaseDatabase.getInstance().reference
        val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        var id= currentUser?.uid.toString()
        var gender=""

        var getData=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb= StringBuilder()
                var username=snapshot.child("Users").child(id).child("username").getValue()
                var email=snapshot.child("Users").child(id).child("email").getValue()
                gender=snapshot.child("Users").child(id).child("gender").getValue().toString()

                sb.append("Username: $username \nEmail: $email \nGender: $gender ")

                binding.homeText.setText(sb)
                if (gender=="Woman"){
                    binding.homeProfilePic.setImageResource(R.drawable.womanlogo)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getData)

        binding.homeChangeButton.setOnClickListener {
            intent= Intent(applicationContext,ChangeProfileScreen::class.java)
            startActivity(intent)
        }
        binding.homeNearButton.setOnClickListener {
            intent=Intent(applicationContext,NearbyCoffeeShopsScreen::class.java)
            startActivity(intent)
        }
        binding.homeFavButton.setOnClickListener {
            intent=Intent(applicationContext,FavouriteScreen::class.java)
            startActivity(intent)
        }
        binding.homeRequestButton.setOnClickListener {
            intent=Intent(applicationContext,ViewRequestScreen::class.java)
            startActivity(intent)
        }
        binding.homeBackButton.setOnClickListener {
            finish()
        }
        binding.homeExitButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            intent=Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun LoadSkeletons(){
        binding.homeProfilePic.loadSkeleton()
        binding.homeText.loadSkeleton()
        binding.homeFavButton.visibility=View.INVISIBLE
        binding.homeNearButton.visibility=View.INVISIBLE
        binding.homeChangeButton.visibility=View.INVISIBLE
        binding.homeRequestButton.visibility=View.INVISIBLE
    }
    fun HideSkeletons(){
        Handler(Looper.getMainLooper()).postDelayed({
            binding.homeProfilePic.hideSkeleton()
            binding.homeText.hideSkeleton()
            binding.homeNearButton.visibility=View.VISIBLE
            binding.homeFavButton.visibility=View.VISIBLE
            binding.homeChangeButton.visibility=View.VISIBLE
            binding.homeRequestButton.visibility=View.VISIBLE
        },2000)
    }
}
