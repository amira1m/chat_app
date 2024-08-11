package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class recycle : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userlist: ArrayList<user>
    private lateinit var Adapter: UerAdapter
    private lateinit var mAuth :FirebaseAuth
    private lateinit var mDbRef : DatabaseReference
    private lateinit var logout_btn : Button
    private lateinit var back_button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle)

        mAuth = FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()
        userlist = ArrayList()
        Adapter = UerAdapter(this , userlist)
        userRecyclerView = findViewById(R.id.recycleview)
        logout_btn =  findViewById(R.id.logout_btn)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.adapter=Adapter


        mDbRef.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for(postSnapshot in snapshot.children){
                    val currentuser = postSnapshot.getValue(user::class.java)
                    if(mAuth.currentUser?.uid!=currentuser?.uid){
                    userlist.add(currentuser!!)}
                }
                Adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        logout_btn.setOnClickListener {
            mAuth.signOut()
            val logout = Intent(this@recycle , log::class.java)
            startActivity(logout)
            finish()
        }


    }



}