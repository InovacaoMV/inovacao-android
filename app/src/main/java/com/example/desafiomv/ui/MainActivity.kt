package com.example.desafiomv.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.ListFragment
import com.example.desafiomv.R
import com.example.desafiomv.model.User
import com.example.desafiomv.ui.contacts.listContacts.ListContactFragment
import com.example.desafiomv.ui.user.signIn.SignInFragment
import com.example.desafiomv.utils.PageAnimation
import com.google.gson.Gson
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var signInFragment: SignInFragment
    private lateinit var listFragment: ListContactFragment
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager
        signInFragment = SignInFragment()
        listFragment = ListContactFragment()
        sharedPreferences = getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)

        val user = getUser()
        if (user == null ) {
            changeFragment(signInFragment, null, R.id.fragment_container)

        } else {
           changeFragment(listFragment, null, R.id.fragment_container)
        }

    }

    private fun changeFragment(fragment: Fragment, pageAnimation: PageAnimation?, containerId: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        if (pageAnimation != null) {
            val enter = pageAnimation.inTransition
            val exit = pageAnimation.outTransition
            if (enter > 0 && exit > 0) {
                fragmentTransaction.setCustomAnimations(enter, exit)
            }
        }

        fragmentTransaction.add(containerId, fragment).commit()
    }

    private fun getUser() : User? {
        val gson = Gson()

        return try {
            gson.fromJson(sharedPreferences!!.getString(getString(R.string.pref_key), null), User::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
