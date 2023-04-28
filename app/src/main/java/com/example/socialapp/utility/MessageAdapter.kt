package com.example.socialapp.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.R
import com.example.socialapp.models.Message
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MessageAdapter(val context: Context, private val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemReceive = 1
    private val itemSent = 2

    inner class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessage : TextView = itemView.findViewById(R.id.sentText)
    }

    inner class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMessage: TextView = itemView.findViewById(R.id.receiveText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1) {
            ReceiveViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.receive, parent, false))
        } else {
            SentViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.sent, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (Firebase.auth.currentUser?.uid == currentMessage.senderID) {
            itemSent
        } else {
            itemReceive
        }
    }


    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.javaClass == SentViewHolder::class.java)
        {
            val viewHolder = holder as SentViewHolder

            holder.sentMessage.text = currentMessage.message
        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }
}