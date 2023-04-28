package com.example.socialapp.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.socialapp.R
import com.example.socialapp.daos.UserDao
import com.example.socialapp.utility.CAdapter
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.fragment_main.*


class ChatActivity : AppCompatActivity() {

    private lateinit var userDao:UserDao
    private lateinit var adapter: CAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setUpRecyclerView()
        supportActionBar?.title = "All Users"
    }

    private fun setUpRecyclerView() {
        userDao = UserDao()
        val usersCollections = userDao.usersCollection
        val query = usersCollections.orderBy("displayName", Query.Direction.ASCENDING)
        val recyclerViewOptions =
            FirestoreRecyclerOptions.Builder<com.example.socialapp.models.User>()
                .setQuery(query, com.example.socialapp.models.User::class.java).build()

        adapter = CAdapter(recyclerViewOptions, this)
        CRecycleView.adapter = adapter
        CRecycleView.layoutManager = LinearLayoutManager(this)
        CRecycleView.itemAnimator = null

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


//    override fun onBackPressed() {
//        finish()
//        super.onBackPressed()
//    }
}

