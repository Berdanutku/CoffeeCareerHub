package com.example.coffeecareerhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.coffeecareerhub.databinding.ActivityCoffeeShopDetailScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.lang.StringBuilder

class CoffeeShopDetailScreen : AppCompatActivity() {
    private lateinit var binding: ActivityCoffeeShopDetailScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCoffeeShopDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var database = FirebaseDatabase.getInstance().reference
        var name=intent.getStringExtra("name")

        var username:String=""
        var email:String=""
        var gender:String=""
        var coffeeName:Any?
        var address:Any?
        var rating:Any?
        var hours:Any?
        var image:Any?
        var id= FirebaseAuth.getInstance().currentUser?.uid

        var getData=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (name?.let { snapshot.child("Users").child(id.toString()).child("Favourites").child(it).exists() } == true){
                    binding.detailRemoveFavButton.visibility=View.VISIBLE
                    binding.detailAddFavButton.visibility=View.GONE
                }
                if (snapshot.child("CoffeeShops").child(name.toString()).child("Requests").child(id.toString()).exists()){
                    binding.detailRemoveRequestButton.visibility=View.VISIBLE
                    binding.detailSendRequestButton.visibility=View.GONE
                }
                var sb= StringBuilder()
                coffeeName= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("Name").getValue() }
                address= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("Address").getValue() }
                rating= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("Rating").getValue() }
                hours= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("Hours").getValue() }
                image= name?.let { snapshot.child("CoffeeShops").child(it).child("0").child("ImageUrl").getValue() }


                sb.append("Name: $coffeeName \nAddress: $address \nRating: $rating \nHours: $hours ")

                binding.detailText.setText(sb)
                Picasso.get().load(image.toString()).into(binding.detailPic)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getData)

        var getUserData=object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                 username= snapshot.child("Users").child(id.toString()).child("username").getValue().toString()
                 email= snapshot.child("Users").child(id.toString()).child("email").getValue().toString()
                 gender= snapshot.child("Users").child(id.toString()).child("gender").getValue().toString()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(getUserData)

        binding.detailBackButton.setOnClickListener {
           finish()
        }
        binding.detailAddFavButton.setOnClickListener {
            if (binding.detailAddFavButton.isVisible){
                if (name != null) {
                    FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Favourites").child(name).setValue(name)
                    binding.detailAddFavButton.visibility= View.GONE
                    binding.detailRemoveFavButton.visibility=View.VISIBLE
                }
            }
        }
        binding.detailRemoveFavButton.setOnClickListener {
            if (binding.detailRemoveFavButton.isVisible){
                if (name != null) {
                    FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Favourites").child(name).removeValue()
                    binding.detailRemoveFavButton.visibility=View.GONE
                    binding.detailAddFavButton.visibility=View.VISIBLE
                }
            }
        }
        binding.detailSendRequestButton.setOnClickListener {
            if (binding.detailSendRequestButton.isVisible){
                FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("Requests").child(id.toString()).child("Username").setValue(username)
                FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("Requests").child(id.toString()).child("Email").setValue(email)
                FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("Requests").child(id.toString()).child("Gender").setValue(gender)
                FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Requests").child(name.toString()).setValue(name)
                FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("Requests").child(id.toString()).child("status").setValue("Not Accepted Yet")
                FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Requests").child(name.toString()).child("status").setValue("Not Accepted Yet")
                binding.detailSendRequestButton.visibility=View.GONE
                binding.detailRemoveRequestButton.visibility=View.VISIBLE
            }
        }
        binding.detailRemoveRequestButton.setOnClickListener {
            if (binding.detailRemoveRequestButton.isVisible){
                FirebaseDatabase.getInstance().reference.child("CoffeeShops").child(name.toString()).child("Requests").child(id.toString()).removeValue()
                FirebaseDatabase.getInstance().reference.child("Users").child(id.toString()).child("Requests").child(name.toString()).removeValue()
                binding.detailRemoveRequestButton.visibility=View.GONE
                binding.detailSendRequestButton.visibility=View.VISIBLE
            }
        }
    }
}


