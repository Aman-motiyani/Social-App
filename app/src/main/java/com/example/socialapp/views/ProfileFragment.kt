package com.example.socialapp.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.socialapp.R
import com.example.socialapp.activities.SignInActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    lateinit var mView: View
    private val auth = Firebase.auth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mView = inflater.inflate(R.layout.fragment_profile, container, false)

        mView.signOutt.setOnClickListener {
            auth.signOut()
            googleSignInClient.signOut()
            Toast.makeText(activity, "Sign out Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }
        return mView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!
        val pName = view.findViewById<TextView>(R.id.profileName)
        val pImage  = view.findViewById<ImageView>(R.id.profilePic)
        pName.text = auth.currentUser?.displayName
        Glide.with(this).load(auth.currentUser?.photoUrl).circleCrop().error(R.drawable.img).into(pImage)
    }
}
