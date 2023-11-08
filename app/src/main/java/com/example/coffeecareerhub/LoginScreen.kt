package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.coffeecareerhub.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLoginScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            if (binding.loginEmail.text.isNotEmpty() && binding.loginPassword.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.loginEmail.text.toString(),binding.loginPassword.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, HomeScreen::class.java)
                        startActivity(intent)
                }
                    else{
                        Toast.makeText(this,"Login not successful.\nEmail or password is wrong.",Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
        binding.loginBackButton.setOnClickListener {
            finish()
        }
    }
}