package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeecareerhub.databinding.ActivityChangeCoffeeShopScreenBinding
import com.google.firebase.database.FirebaseDatabase

class ChangeCoffeeShopScreen : AppCompatActivity() {
    private lateinit var binding: ActivityChangeCoffeeShopScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChangeCoffeeShopScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var name=intent.getStringExtra("name")

        binding.changeCoffeeShopSaveButton.setOnClickListener {
            FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("0").child("Name").setValue(binding.changeCoffeeShopName.text.toString())
            FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("0").child("Address").setValue(binding.changeCoffeeShopAddress.text.toString())
            FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("0").child("Hours").setValue(binding.changeCoffeeShopHours.text.toString())
            FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("0").child("ImageUrl").setValue(binding.changeCoffeeShopPic.text.toString())

            intent= Intent(applicationContext,OwnerCoffeeShopScreen::class.java)
            intent.putExtra("name",name)
            startActivity(intent)
        }
        binding.changeBackButton2.setOnClickListener {
            finish()
        }
    }
}