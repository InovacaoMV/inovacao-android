package com.example.desafiomv.ui.user.signIn

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.desafiomv.R

import com.example.desafiomv.databinding.SignInFragmentBinding
import com.example.desafiomv.model.User
import com.example.desafiomv.ui.BaseFragment
import com.example.desafiomv.ui.contacts.listContacts.ListContactFragment
import com.example.desafiomv.utils.NetworkChange
import com.example.desafiomv.utils.PageAnimation
import com.example.desafiomv.utils.ValidateFields
import com.google.gson.Gson
import org.jetbrains.anko.design.snackbar
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import java.util.regex.Pattern

class SignInFragment : BaseFragment() {

    private lateinit var mBinding: SignInFragmentBinding
    private lateinit var listContactFragment: ListContactFragment
    private lateinit var signInFragment: SignInFragment
    private lateinit var networkChange: NetworkChange
    private var sharedPreferences: SharedPreferences? = null

    private val mViewModel: SignInViewModel by lazy {
        ViewModelProviders.of(this).get(SignInViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false)
        mBinding.viewModel = mViewModel

        sharedPreferences = context!!.getSharedPreferences(getString(R.string.preferences), Context.MODE_PRIVATE)

        listContactFragment = ListContactFragment()
        signInFragment = SignInFragment()
        networkChange =
            NetworkChange(context = activity!!.baseContext)

        lifecycle.addObserver (mViewModel)
        observer()

        mViewModel.setPreferences(sharedPreferences!!)
        if(arguments != null ) {
            val user = arguments!!.get("user") as User
                mBinding.toolbar.toolbarLayout.visibility = View.VISIBLE
                mBinding.toolbar.titleToolbarTv.text = getString(R.string.settings)
                mBinding.toolbar.backIv.visibility = View.VISIBLE
                (activity as AppCompatActivity).setSupportActionBar(mBinding.toolbar.myToolbar)
                mBinding.toolbar.backIv.setOnClickListener {
                    changeFragment(listContactFragment, PageAnimation.SLIDE_LEFT_TO_RIGHT, R.id.fragment_container, activity!!.supportFragmentManager)

                }

            mViewModel.setUserParams(user)
            if (user != null) {
                updateAccount()
            }
        } else {
            createAccount()
        }

        return mBinding.root
    }

    private fun message(msg: String) {
        view?.snackbar(msg)
    }

    private fun observer() {
        mViewModel.error.observe(this, Observer {
            val message = when (it) {
                is UnknownHostException,
                is IOException -> getString(R.string.network_error)
                is HttpException -> getString(R.string.invalid_parameters_error)
                else -> getString(R.string.unknown_error)
            }
            message(message)
        })

        mViewModel.msg.observe(this, Observer {
            message(it)
        })

        mViewModel.createAccount.observe(this, Observer {
            val editor = sharedPreferences!!.edit()

            val gson = Gson()
            it.password = ""
            editor.putString("pref_key_user", gson.toJson(it))
            editor.apply()

            changeFragment(listContactFragment, PageAnimation.SLIDE_LEFT_TO_RIGHT, R.id.fragment_container, activity!!.supportFragmentManager)
        })

        mViewModel.deleteAccount.observe(this, Observer {
            sharedPreferences!!.edit().clear().apply()
            changeFragment(signInFragment, PageAnimation.SLIDE_LEFT_TO_RIGHT, R.id.fragment_container, activity!!.supportFragmentManager)

        })
    }

    private fun createAccount() {

        var flag = true
        mBinding.createLogin.setOnClickListener {
            if (flag) {
                if (ValidateFields.validEmail(mBinding.emailTextInputText, mBinding.emailTextInputLayout, context!!)
                    && ValidateFields.validPassword(mBinding.passwordTextInputText, mBinding.passwordTextInputLayout, context!!)) {
                    if (networkChange.hasInternetConnection()) {
                        mViewModel.loginUser()
                    } else {
                        message(getString(R.string.no_connection))
                    }
                }
            } else {
                if (ValidateFields.validEmail(mBinding.emailTextInputText, mBinding.emailTextInputLayout, context!!)
                    && ValidateFields.validPassword(mBinding.passwordTextInputText, mBinding.passwordTextInputLayout, context!!)) {
                    if (networkChange.hasInternetConnection()) {

                        mViewModel.insert()
                    } else {
                        message(getString(R.string.no_connection))
                    }
                }
            }
        }

        mBinding.createDelete.setOnClickListener {
            if (flag) {
                mBinding.createDelete.text = getString(R.string.login)
                mBinding.createLogin.text = getString(R.string.create_account)
                flag = false
            } else {
                mBinding.createDelete.text = getString(R.string.create_account)
                mBinding.createLogin.text = getString(R.string.login)
                flag = true
            }
        }
    }

    private fun updateAccount() {
        mBinding.createDelete.text = getString(R.string.delete_account)
        mBinding.createLogin.text = getString(R.string.update_account)

        mViewModel.getUser()

        mBinding.createDelete.setOnClickListener {
            mViewModel.deleteUser()
        }

        mBinding.createLogin.setOnClickListener {
            if (ValidateFields.validEmail(mBinding.emailTextInputText, mBinding.emailTextInputLayout, context!!)
                && ValidateFields.validPassword(mBinding.passwordTextInputText, mBinding.passwordTextInputLayout, context!!)) {
                mViewModel.update()
            }
        }
    }

}
