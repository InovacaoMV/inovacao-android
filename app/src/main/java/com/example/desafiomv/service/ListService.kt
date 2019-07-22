package com.example.desafiomv.service

import com.example.desafiomv.config.RetrofitConfig
import com.example.desafiomv.model.Contact
import com.example.desafiomv.model.ContactDTO
import io.reactivex.Observable
import io.reactivex.Single

object ListService {

    fun getContactList (userEmail: String) : Single<List<Contact>> {
        return RetrofitConfig.instance().getContactList(userEmail)
    }

    fun deleteContactList (contact: Contact) : Single<Contact> {
        return RetrofitConfig.instance().deleteContactList(contact)
    }

    fun saveContactList (contact: ContactDTO) : Single<Contact> {
        return RetrofitConfig.instance().saveContactList(contact)
    }

    fun updateContactList (contact: Contact) : Single<List<Contact>> {
        return RetrofitConfig.instance().updateContactList(contact)
    }

}