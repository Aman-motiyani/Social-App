package com.example.socialapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.socialapp.R
import com.example.socialapp.daos.PostDao
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.android.synthetic.main.fragment_post.view.*


class PostFragment : Fragment() {

    private lateinit var postDao: PostDao
    lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postDao = PostDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        mView = inflater.inflate(R.layout.fragment_post, container, false)

        mView.postbutton.setOnClickListener {
            val input = posttext.text.toString().trim()
            if (input.isNotEmpty())
            {
                postDao.addPost(input)
                loadFragment(MainFragment())
            }
        }

        return mView
    }



    private fun loadFragment(fragment: Fragment) {
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }



}
