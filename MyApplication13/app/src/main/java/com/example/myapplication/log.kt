package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class log : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    private lateinit var  email_edit : TextInputLayout
    private lateinit var  password_edit : TextInputLayout
    private lateinit var  signupp_btn : Button
    private lateinit var  login_btn : Button
    private lateinit var  email_11 : TextInputEditText
    private lateinit var  password_11 : TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)
        login_btn = findViewById(R.id.login)

        email_edit = findViewById(R.id.email_ed)
        password_edit = findViewById(R.id.password_ed)
        signupp_btn= findViewById(R.id.sign1)
        email_11=findViewById(R.id.txt_email)
        password_11 = findViewById(R.id.txt_pass)
        mAuth = FirebaseAuth.getInstance()

        signupp_btn.setOnClickListener {
    val intent = Intent(this , MainActivity::class.java)
    startActivity(intent)
}

        login_btn.setOnClickListener {
            val email = email_11.text.toString()
            val password = password_11.text.toString()
            login(email,password)
        }
    }
    private fun login(email:String , password :String ){

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    val intentt =Intent(this@log,recycle::class.java)
                    startActivity(intentt)
                    finish()
                    //println("yesssssssssssssssssssss")
                } else {

                    Toast.makeText(this@log," doesn't exist" , Toast.LENGTH_SHORT).show()
             //println("noooooooooooooooooooooooooooooooooooooooooooooooooo")
                }

    }}
}