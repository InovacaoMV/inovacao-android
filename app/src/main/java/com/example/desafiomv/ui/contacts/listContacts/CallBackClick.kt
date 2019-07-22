package com.example.desafiomv.ui.contacts.listContacts

import com.example.desafiomv.model.Contact

interface CallBackClick {

    fun onClickDelete(contact: Contact)

    fun onClickUpdate(contact: Contact)
}