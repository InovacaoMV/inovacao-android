package com.example.desafiomv.service

import com.example.desafiomv.config.RetrofitConfig
import com.example.desafiomv.model.Contact
import io.reactivex.Observable
import io.reactivex.Single

object ListService {

    fun getContactList (userEmail: String) : Observable<Contact> {
        return RetrofitConfig.instance().getContactList(userEmail)
            .flatMap { contacts -> Observable.fromIterable(contacts) }
            .map {
                Contact(
                    it.id,
                    it.personEmail,
                    it.name,
                    it.userEmail,
                    it.cellPhone,
                    it.error
                )
            }
    }

    fun deleteContactList (contact: Contact) : Single<Contact> {
        return RetrofitConfig.instance().deleteContactList(contact)
    }

    fun saveContactList (contact: Contact) : Single<Contact> {
        return RetrofitConfig.instance().saveContactList(contact)
    }

    fun updateContactList (contact: Contact) : Single<Contact> {
        return RetrofitConfig.instance().updateContactList(contact)
    }

}