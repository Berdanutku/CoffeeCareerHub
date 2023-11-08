package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.coffeecareerhub.databinding.ActivitySignupScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySignupScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genders = resources.getStringArray(R.array.Gender)
        if (binding.signUpGender != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
            binding.signUpGender.adapter = adapter

            binding.signUpButton.setOnClickListener {
                if (binding.signUpUsername.text.isNotEmpty() && binding.signUpEmail.text.isNotEmpty() && binding.signUpPassword.text.isNotEmpty() && binding.signUpRePassword.text.isNotEmpty()) {
                    if (binding.signUpPassword.text.toString().equals(binding.signUpRePassword.text.toString())
                    ) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                            binding.signUpEmail.text.toString(),
                            binding.signUpPassword.text.toString()
                        ).addOnCompleteListener{
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.signUpEmail.text.toString(),binding.signUpPassword.text.toString()).addOnCompleteListener {
                                val selected = binding.signUpGender.selectedItem.toString()
                                val user=User(binding.signUpUsername.text.toString(),binding.signUpEmail.text.toString(),selected)
                                if (it.isSuccessful) {
                                    var id = FirebaseAuth.getInstance().currentUser?.uid
                                    FirebaseDatabase.getInstance().reference.child("Users").child(id.toString())
                                        .setValue(user)
                                    intent = Intent(applicationContext, JobDetailsScreen::class.java)
                                    intent.putExtra("id",id)
                                    startActivity(intent)
                                }
                            }
                        }
                        Toast.makeText(applicationContext, "Sign up successful", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this, "Passwords are not same!", Toast.LENGTH_LONG).show()
                    }

                }
                else{
                    Toast.makeText(applicationContext,"Enter all informations!",Toast.LENGTH_LONG).show()
                }
            }
            binding.signUpBackButton.setOnClickListener {
                finish()
            }

        }
    }
}