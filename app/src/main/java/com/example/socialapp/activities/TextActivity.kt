package com.example.socialapp.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.R
import com.example.socialapp.models.Message
import com.example.socialapp.utility.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_text.*

class TextActivity : AppCompatActivity() {

    private lateinit var messageList : ArrayList<Message>
    private var receiverRoom: String? = null
    private var senderRoom:String? = null
    private lateinit var db: DatabaseReference
    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        db = Firebase.database.reference

        val name = intent.getStringExtra("name")
        val receiverId = intent.getStringExtra("uid")

        val senderId = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverId + senderId
        receiverRoom = senderId + receiverId

        supportActionBar?.title = name
        messageList = ArrayList()
        textRecycleView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter(this, messageList)
        textRecycleView.adapter = messageAdapter

        db.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (postSnapshot in snapshot.children) {
                    val message = postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }

                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        imageButton.setOnClickListener {
            val message = editTextTextPersonName2.text.toString()
            val messageObject  = Message(message,senderId)
            db.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    db.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            editTextTextPersonName2.setText("")
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}