package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.widget.Toast
import java.util.Locale

class chatactivity : AppCompatActivity() {
     private  lateinit var chatrecycleview : RecyclerView
    private  lateinit var messagebox : EditText
    private  lateinit var send_btn : ImageView
    private  lateinit var messagelist : ArrayList<message>
    private lateinit var messageAdapter : messageadapt
    private lateinit var mDbRef : DatabaseReference
    private lateinit var back_button :Button
    var senderRoom : String? = null
    var recvRoom : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatactivity)

        val name  = intent.getStringExtra("name")
        val recvuid  = intent.getStringExtra("uid")
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()
        senderRoom = recvuid + senderuid
         recvRoom = senderuid +recvuid

        supportActionBar?.title = name
        chatrecycleview = findViewById(R.id.chatrecycle)
        messagebox = findViewById(R.id.message)
        send_btn = findViewById(R.id.imagee_button)
        messagelist = ArrayList()
        messageAdapter = messageadapt(this , messagelist)
//        voice_btn = findViewById(R.id.voice_btn)
        chatrecycleview.layoutManager = LinearLayoutManager(this)
        chatrecycleview.adapter = messageAdapter
        back_button= findViewById(R.id.back_btn)

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                 for (postsnapshot in snapshot.children){
                     val message = postsnapshot.getValue(message::class.java)
                     messagelist.add(message!!)
                 }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        send_btn.setOnClickListener {

            val massage = messagebox.text.toString()
            val messageobject = message( massage , senderuid)


            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageobject).addOnSuccessListener {
                    mDbRef.child("chats").child(recvRoom!!).child("messages").push()
                        .setValue(messageobject)
                }
            messagebox.setText("")

        }
        back_button.setOnClickListener {
            var move_back = Intent(this@chatactivity , recycle::class.java)
            startActivity(move_back)
            finish()
        }



    }


}
