package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeecareerhub.databinding.ActivityOwnerCoffeeShopScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class OwnerCoffeeShopScreen : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerCoffeeShopScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerCoffeeShopScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var database=FirebaseDatabase.getInstance().reference
        var name=intent.getStringExtra("name")

        var getData=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb= StringBuilder()
                var coffeeName= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("Name").getValue() }
                var address= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("Address").getValue() }
                var rating= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("Rating").getValue() }
                var hours= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("Hours").getValue() }
                var image= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("ImageUrl").getValue() }

                sb.append("Name: $coffeeName \nAddress: $address \nRating: $rating \nHours: $hours ")

                binding.ownerCoffeeShopText.setText(sb)
                Picasso.get().load(image.toString()).into(binding.ownerCoffeeShopPic)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getData)

        binding.ownerCoffeeShopBackButton.setOnClickListener {
            finish()
        }
        binding.ownerCoffeeShopEditButton.setOnClickListener {
            intent= Intent(applicationContext,ChangeCoffeeShopScreen::class.java)
            intent.putExtra("name",name.toString())
            startActivity(intent)
        }
        binding.ownerCoffeeShopViewButton.setOnClickListener {
            intent=Intent(applicationContext,OwnerViewRequestScreen::class.java)
            intent.putExtra("name",name.toString())
            startActivity(intent)
        }
    }
}