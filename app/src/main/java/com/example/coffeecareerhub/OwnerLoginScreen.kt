package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeecareerhub.databinding.ActivityOwnerLoginScreenBinding

class OwnerLoginScreen : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ownerLoginButton.setOnClickListener {
            if (binding.ownerLoginUsername.text.isNotEmpty()&&binding.ownerLoginPassword.text.isNotEmpty()){
                intent= Intent(applicationContext,OwnerCoffeeShopScreen::class.java)
                intent.putExtra("name",binding.ownerLoginUsername.text.toString())
                startActivity(intent)
            }
        }
        binding.ownerLoginBackButton.setOnClickListener {
            finish()
        }
    }
}