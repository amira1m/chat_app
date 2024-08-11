package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class messageadapt(val context:Context ,val messagelist : ArrayList<message> ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
val ITEM_RECV =1
val ITEM_SEND = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
if (viewType==1){
    val view:View = LayoutInflater.from(context).inflate(R.layout.recieve , parent , false)
    return recieveviewholder(view)
}
else {
    val view:View = LayoutInflater.from(context).inflate(R.layout.sent , parent , false)
    return sentviewholder(view)
}

    }


    override fun getItemCount(): Int {
        return  messagelist.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage = messagelist[position]

        if(holder.javaClass == sentviewholder ::class.java){
            val viewHolder = holder as sentviewholder
            holder.text_send.text = currentmessage.message
            holder.senderLayout.visibility = View.VISIBLE





        }else{
            val viewHolder = holder as recieveviewholder
            holder.rcv_send.text = currentmessage.message
            holder.receiverLayout.visibility = View.VISIBLE
        }


    }

    override fun getItemViewType(position: Int): Int {
        val currentmessage = messagelist[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderid)){
            return ITEM_SEND
        } else {
            return  ITEM_RECV
        }
    }

    class sentviewholder (itemView: View) : RecyclerView.ViewHolder(itemView){
val text_send = itemView.findViewById<TextView>(R.id.tv_message)
        val senderLayout: RelativeLayout = itemView.findViewById(R.id.sender_layout)
    }

    class recieveviewholder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val rcv_send = itemView.findViewById<TextView>(R.id.output)
        val receiverLayout: RelativeLayout = itemView.findViewById(R.id.receiver_layout)

    }
}