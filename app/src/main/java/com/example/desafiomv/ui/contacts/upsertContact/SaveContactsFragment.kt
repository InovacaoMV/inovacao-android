package com.example.desafiomv.ui.contacts.upsertContact

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.Observer

import com.example.desafiomv.R
import com.example.desafiomv.databinding.ListContactFragmentBinding
import com.example.desafiomv.databinding.SaveContactsFragmentBinding
import com.example.desafiomv.model.Contact
import com.example.desafiomv.model.User
import com.example.desafiomv.ui.BaseFragment
import com.example.desafiomv.ui.contacts.listContacts.ListContactFragment
import com.example.desafiomv.ui.contacts.listContacts.ListContactViewModel
import com.example.desafiomv.ui.user.signIn.SignInFragment
import com.example.desafiomv.utils.MaskEditUtil
import com.example.desafiomv.utils.NetworkChange
import com.example.desafiomv.utils.PageAnimation
import com.example.desafiomv.utils.ValidateFields
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.lang.Exception
import java.util.regex.Pattern

class SaveContactsFragment : BaseFragment() {

    private lateinit var mBinding: SaveContactsFragmentBinding
    private lateinit var listFragment: ListContactFragment
    private lateinit var networkChange: NetworkChange
    private var sharedPreferences: SharedPreferences? = null

    private val mViewModel: SaveContactsViewModel by lazy {
        ViewModelProviders.of(this).get(SaveContactsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.save_contacts_fragment, container, false)
        mBinding.viewModel = mViewModel

        listFragment = ListContactFragment()
        sharedPreferences = context!!.getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)

        networkChange =
            NetworkChange(context = activity!!.baseContext)

        lifecycle.addObserver (mViewModel)
        observer()

        mBinding.phoneTextInputText.addTextChangedListener(MaskEditUtil.mask(mBinding.phoneTextInputText, "(##)#####-####"))

        mViewModel.setUser(getUser())

        if(arguments != null ) {
            val contact = arguments!!.get("contact") as Contact
            mViewModel.setContactsParams(contact)
            if (contact != null) {
                update()
            }
        } else {
            create()
        }

        mBinding.toolbar.toolbarLayout.visibility = View.VISIBLE
        mBinding.toolbar.titleToolbarTv.text = getString(R.string.contacts)
        mBinding.toolbar.backIv.visibility = View.VISIBLE
        (activity as AppCompatActivity).setSupportActionBar(mBinding.toolbar.myToolbar)
        mBinding.toolbar.backIv.setOnClickListener {
            changeFragment(listFragment, PageAnimation.SLIDE_RIGHT_TO_LEFT, R.id.fragment_container, activity!!.supportFragmentManager)

        }

        return mBinding.root
    }

    private fun message(msg: String) {
        view?.snackbar(msg)
    }

    private fun create() {
        mBinding.save.setOnClickListener {
            if (networkChange.hasInternetConnection()) {
                if (ValidateFields.validEmail(mBinding.emailTextInputText, mBinding.emailTextInputLayout, context!!)
                    && ValidateFields.validName(mBinding.nameTextInputText, mBinding.nameTextInputLayout, context!!)
                    && ValidateFields.validPhone(mBinding.phoneTextInputText, mBinding.phoneTextInputLayout, context!!)) {
                    mViewModel.insert()
                }
            } else {
                message(getString(R.string.no_connection))
            }
        }
    }

    private fun update () {
        mBinding.save.setOnClickListener {
            if (networkChange.hasInternetConnection()) {
                if (ValidateFields.validEmail(mBinding.emailTextInputText, mBinding.emailTextInputLayout, context!!)
                    && ValidateFields.validName(mBinding.nameTextInputText, mBinding.nameTextInputLayout, context!!)
                    && ValidateFields.validPhone(mBinding.phoneTextInputText, mBinding.phoneTextInputLayout, context!!)) {
                    mViewModel.update()
                }
            } else {
                message(getString(R.string.no_connection))
            }
        }
    }

    private fun observer() {
       mViewModel.save.observe(this, Observer {
            message(getString(R.string.success_save))
       })
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
