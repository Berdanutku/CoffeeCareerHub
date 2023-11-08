package com.example.coffeecareerhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import com.example.coffeecareerhub.databinding.ActivityFavouriteScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavouriteScreen : AppCompatActivity() {
    private lateinit var binding: ActivityFavouriteScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavouriteScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database=FirebaseDatabase.getInstance().reference
        var id= FirebaseAuth.getInstance().currentUser?.uid
        val list= mutableListOf<String>()
        var getData=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.linearLayout.removeAllViews()
                val title=TextView(this@FavouriteScreen)
                title.text="Favourite Coffee Shops"
                title.textSize= 34F
                title.gravity=Gravity.CENTER
                title.setTextColor(resources.getColor(R.color.purple))
                binding.linearLayout.addView(title)
                for (x in snapshot.child("Users").child(id.toString()).child("Favourites").children){
                    var child=x.getValue()
                    list.add(child.toString())
                }
                for (name in list){
                    val newButton=Button(this@FavouriteScreen)
                    newButton.text=name
                    newButton.setTextColor(resources.getColor(R.color.white))
                    newButton.setBackgroundColor(resources.getColor(R.color.purple))
                    newButton.setOnClickListener {
                        intent=Intent(applicationContext,CoffeeShopDetailScreen::class.java)
                        intent.putExtra("name",name)
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