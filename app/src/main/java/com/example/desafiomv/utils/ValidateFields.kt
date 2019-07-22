package com.example.desafiomv.utils

import android.content.Context
import com.example.desafiomv.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

object ValidateFields {

    fun validEmail(text: TextInputEditText, layout: TextInputLayout, context: Context) : Boolean {
        val email = text.text.toString().trim()

        val regexEmail = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+\$"

        val matcher = Pattern.compile(regexEmail).matcher(email)

        return if (email.isEmpty()) {
            layout.error = context.getString(R.string.email_empty)
            false
        } else if (!matcher.matches()) {
            layout.error = context.getString(R.string.email_invalid)
            false
        } else {
            layout.error = null
            true
        }
    }

    fun validName(text: TextInputEditText, layout: TextInputLayout, context: Context) : Boolean {
        val name = text.text.toString().trim()

        return if (name.isEmpty()) {
            layout.error = context.getString(R.string.email_empty)
            false
        }  else {
            layout.error = null
            true
        }
    }

    fun validPhone(text: TextInputEditText, layout: TextInputLayout, context: Context) : Boolean {
        val phone = text.text.toString().trim()

        return if (phone.isEmpty()) {
            layout.error = context.getString(R.string.email_empty)
            false
        }  else {
            layout.error = null
            true
        }
    }

     fun validPassword(text: TextInputEditText, layout: TextInputLayout, context: Context) : Boolean {
        val password =text.text.toString().trim()

        return if (password.isEmpty()) {
            layout.error = context.getString(R.string.password_empty)
            false
        }
        else if (password.length <= 5) {
            layout.error = context.getString(R.string.password_low)
            false
        }
        else {
            layout.error = null
            true
        }
    }


}