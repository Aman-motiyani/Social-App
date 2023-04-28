package com.example.socialapp.utility

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialapp.R
import com.example.socialapp.activities.TextActivity
import com.example.socialapp.models.Message
import com.example.socialapp.models.Post
import com.example.socialapp.models.User
import com.example.socialapp.views.MainFragment
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class CAdapter(options: FirestoreRecyclerOptions<User>, private val context: Context) : FirestoreRecyclerAdapter<User, CAdapter.CViewHolder>(
    options
){

        inner class CViewHolder(itemView: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
        {
//          val msgTime: android.widget.TextView = itemView.findViewById(com.example.socialapp.R.id.msgTime)
            val userName: TextView = itemView.findViewById(R.id.CUserName)
            val userImage: ImageView = itemView.findViewById(R.id.CUserImage)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CViewHolder {
        return CViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.userlist, parent, false))

    }

    override fun onBindViewHolder(holder: CViewHolder, position: Int, model: User) {
        holder.userName.text = model.displayName
        Glide.with(holder.userImage.context).load(model.imageUrl).circleCrop().into(holder.userImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context,TextActivity::class.java)
            intent.putExtra("name",model.displayName)
            intent.putExtra("uid",model.uid)
            context.startActivity(intent)
        }
    }
}