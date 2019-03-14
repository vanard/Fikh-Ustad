package com.iffy.fikhustaz.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()
        currentUser = mAuth.currentUser
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        name_tv_home.text = currentUser?.displayName
        email_tv_home.text = currentUser?.email
    }

    override fun onStart() {
        super.onStart()
        FirebaseUtil.getCurrentUser {
            if (this@HomeFragment.isVisible){
                email_tv_home.text = it.email
                phone_tv_home.text = it.handphone
            }
        }
    }
}
