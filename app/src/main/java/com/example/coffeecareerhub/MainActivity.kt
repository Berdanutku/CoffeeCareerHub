package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.coffeecareerhub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.firstSignUpButton.setOnClickListener {
            intent= Intent(applicationContext,SignupScreen::class.java)
            startActivity(intent)
        }
        binding.firstLoginButton.setOnClickListener {
            intent=Intent(applicationContext,LoginScreen::class.java)
            startActivity(intent)
        }
        binding.firstOwnerButton.setOnClickListener {
            intent=Intent(applicationContext,OwnerLoginScreen::class.java)
            startActivity(intent)
        }
    }
}