package com.example.desafiomv.ui.contacts.listContacts

import com.example.desafiomv.model.Contact

interface CallBackClick {

    fun onClickDelete()

    fun onClickUpdate(contact: Contact)
}