package com.example.myapplication

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
     private lateinit var mAuth : FirebaseAuth
    private lateinit var  backk_btn : Button
    private lateinit var  signupp_btn : Button
    private lateinit var  log_name : TextInputEditText
    private lateinit var  log_pass : TextInputEditText
    private lateinit var  log_email : TextInputEditText
    private lateinit var  mDbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        backk_btn= findViewById(R.id.back)
        signupp_btn= findViewById(R.id.sign_btn2)
        log_name= findViewById(R.id.log_name)
        log_pass= findViewById(R.id.log_pass)
        log_email= findViewById(R.id.log_email)

        mAuth = FirebaseAuth.getInstance()

backk_btn.setOnClickListener {
    val intent_back = Intent(this@MainActivity , log::class.java)
    startActivity(intent_back)
}
        signupp_btn.setOnClickListener {
            val name =log_name.text.toString()
            val email = log_email.text.toString()
            val password = log_pass.text.toString()

            signup(name, email,password)
        }
    }
    private fun signup(name : String, email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intentt =Intent(this@MainActivity,recycle::class.java)
                    addUserToDatabase(name , email , mAuth.currentUser?.uid!!)
                    startActivity(intentt)
                    finish()

                } else {


  Toast.makeText(this@MainActivity,"error accured" ,Toast.LENGTH_SHORT).show()

                }

            }
    }
fun addUserToDatabase(name: String,email: String,uid:String){
mDbRef=FirebaseDatabase.getInstance().getReference()
    mDbRef.child("user").child(uid).setValue(user(name, email, uid))

}
}