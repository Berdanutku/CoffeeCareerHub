package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import com.example.coffeecareerhub.databinding.ActivityOwnerViewRequestScreenBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OwnerViewRequestScreen : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerViewRequestScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOwnerViewRequestScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var name=intent.getStringExtra("name")

        val database= FirebaseDatabase.getInstance().reference
        val list= mutableListOf<String>()
        var getData=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.linearLayout.removeAllViews()
                val title= TextView(this@OwnerViewRequestScreen)
                title.text="Job Requests"
                title.textSize= 34F
                title.gravity= Gravity.CENTER
                title.setTextColor(resources.getColor(R.color.purple))
                binding.linearLayout.addView(title)
                for (x in snapshot.child("CoffeeShops").child(name.toString()).child("Requests").children){
                    var child=x.key
                    list.add(child.toString())
                }
                for (id in list){
                    var username=snapshot.child("Users").child(id).child("username").getValue()
                    var email=snapshot.child("Users").child(id).child("email").getValue()
                    var gender=snapshot.child("Users").child(id).child("gender").getValue()
                    val newButton= Button(this@OwnerViewRequestScreen)
                    newButton.text=username.toString()
                    newButton.setTextColor(resources.getColor(R.color.white))
                    newButton.setBackgroundColor(resources.getColor(R.color.purple))
                    var statusAcceptButton=""
                    if (snapshot.child("Users").child(id.toString()).child("Requests").child(name.toString()).child("status").getValue()=="Accepted"){
                        statusAcceptButton="Accepted"
                    }
                    else{
                        statusAcceptButton="Refused"
                    }

                    newButton.setOnClickListener {
                        intent=Intent(applicationContext,OwnerRequestDetailScreen::class.java)
                        intent.putExtra("username",username.toString())
                        intent.putExtra("email",email.toString())
                        intent.putExtra("gender",gender.toString())
                        intent.putExtra("id",id)
                        intent.putExtra("name",name)
                        intent.putExtra("statusAcceptButton",statusAcceptButton)
                        startActivity(intent)
                    }
                    binding.linearLayout.addView(newButton)
                }
                list.clear()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        database.addValueEventListener(getData)
    }
}