package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import com.example.coffeecareerhub.databinding.ActivityViewRequestScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewRequestScreen : AppCompatActivity() {
    private lateinit var binding: ActivityViewRequestScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityViewRequestScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database=FirebaseDatabase.getInstance().reference
        var id= FirebaseAuth.getInstance().currentUser?.uid
        val list= mutableListOf<String>()

        var getUserData=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.linearLayout.removeAllViews()
                val title= TextView(this@ViewRequestScreen)
                title.text="Job Requests"
                title.textSize= 34F
                title.gravity= Gravity.CENTER
                title.setTextColor(resources.getColor(R.color.purple))
                binding.linearLayout.addView(title)
                for (x in snapshot.child("Users").child(id.toString()).child("Requests").children){
                    var child=x.key
                    list.add(child.toString())
                }
                for (name in list){
                    val newButton= Button(this@ViewRequestScreen)
                    newButton.text=name
                    newButton.setTextColor(resources.getColor(R.color.white))
                    newButton.setBackgroundColor(resources.getColor(R.color.purple))
                    newButton.setOnClickListener {
                        intent= Intent(applicationContext,CoffeeShopDetailScreen::class.java)
                        intent.putExtra("name",name)
                        startActivity(intent)
                    }
                    binding.linearLayout.addView(newButton)
                    val text=TextView(this@ViewRequestScreen)
                    text.text="Status of the request: "+snapshot.child("Users").child(id.toString()).child("Requests").child(name.toString()).child("status").getValue().toString()
                    text.gravity=Gravity.CENTER
                    text.textSize=24F
                    text.setTextColor(resources.getColor(R.color.purple))
                    binding.linearLayout.addView(text)
                }
                list.clear()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getUserData)
    }
}