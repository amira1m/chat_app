package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UerAdapter (val context: Context , val userlist: ArrayList<user>):
    RecyclerView.Adapter<UerAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.user , parent , false)
    return UserViewHolder(view)

    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

            val currentuser = userlist[position]
            holder.recyl_txt.text = currentuser.name
            holder.itemView.setOnClickListener {
            val intent = Intent(context , chatactivity::class.java)
            intent.putExtra("name" , currentuser.name)
            intent.putExtra("uid" , currentuser.uid)
            context.startActivity(intent)
        }


    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recyl_txt = itemView.findViewById<TextView>(R.id.txt_recyl)
    }
}