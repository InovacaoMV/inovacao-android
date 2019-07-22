package com.example.desafiomv.ui.contacts.listContacts

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiomv.R
import com.example.desafiomv.databinding.ListContactFragmentBinding
import com.example.desafiomv.model.Contact
import com.example.desafiomv.model.User
import com.example.desafiomv.ui.BaseFragment
import com.example.desafiomv.ui.contacts.upsertContact.SaveContactsFragment
import com.example.desafiomv.ui.user.signIn.SignInFragment
import com.example.desafiomv.utils.NetworkChange
import com.example.desafiomv.utils.PageAnimation
import com.google.gson.Gson
import java.lang.Exception

class ListContactFragment : BaseFragment(), CallBackClick{

    private lateinit var mBinding: ListContactFragmentBinding
    private lateinit var signInFragment: SignInFragment
    private lateinit var saveContactsFragment: SaveContactsFragment
    private lateinit var networkChange: NetworkChange
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var mContactAdapter: ContactAdapter

    private val mViewModel: ListContactViewModel by lazy {
        ViewModelProviders.of(this).get(ListContactViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.list_contact_fragment, container, false)
        mBinding.viewModel = mViewModel

        signInFragment = SignInFragment()
        saveContactsFragment = SaveContactsFragment()
        sharedPreferences = context!!.getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)

        networkChange =
            NetworkChange(context = activity!!.baseContext)

        lifecycle.addObserver (mViewModel)

        initRecycler()

        observables()
        val user = getUser()
        mViewModel.setUser(user!!)

        mBinding.toolbar.toolbarLayout.visibility = View.VISIBLE
        mBinding.toolbar.titleToolbarTv.text = getString(R.string.contacts)
        mBinding.toolbar.settingIv.visibility = View.VISIBLE
        (activity as AppCompatActivity).setSupportActionBar(mBinding.toolbar.myToolbar)
        mBinding.toolbar.settingIv.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("user", user)
            signInFragment.arguments = bundle
            changeFragment(signInFragment, PageAnimation.SLIDE_RIGHT_TO_LEFT, R.id.fragment_container, activity!!.supportFragmentManager)

        }

        mBinding.flabPlus.setOnClickListener {
            changeFragment(saveContactsFragment, PageAnimation.SLIDE_LEFT_TO_RIGHT, R.id.fragment_container, activity!!.supportFragmentManager)
        }

        return mBinding.root
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

    private fun observables() {
        mViewModel.contactMutableLiveData.observe(this, Observer {
            mContactAdapter.setData(it, this)
        })
    }

    private fun initRecycler() {
        val layoutManager = LinearLayoutManager(context)
        mBinding.rcList.layoutManager = layoutManager
        mContactAdapter = ContactAdapter()
        mBinding.rcList.adapter = mContactAdapter
    }

    override fun onClickDelete() {
        mViewModel.delete()
    }

    override fun onClickUpdate(contact: Contact) {
        val bundle = Bundle()
        bundle.putSerializable("contact", contact)
        saveContactsFragment.arguments = bundle
        changeFragment(saveContactsFragment, PageAnimation.SLIDE_LEFT_TO_RIGHT, R.id.fragment_container, activity!!.supportFragmentManager)
    }

}
