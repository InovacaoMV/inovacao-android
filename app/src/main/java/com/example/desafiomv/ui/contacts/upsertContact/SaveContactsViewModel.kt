package com.example.desafiomv.ui.contacts.upsertContact

import androidx.lifecycle.*
import com.example.desafiomv.model.Contact
import com.example.desafiomv.model.ContactDTO
import com.example.desafiomv.model.User
import com.example.desafiomv.service.ListService
import com.example.desafiomv.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SaveContactsViewModel : ViewModel(), LifecycleObserver {

    private var contactMutableLiveData = MutableLiveData<Contact>()
    var error = SingleLiveEvent<Throwable>()
    var msg = SingleLiveEvent<String>()
    private var disposables: CompositeDisposable = CompositeDisposable()
    var save = SingleLiveEvent<Void>()
    private lateinit var user: User

    fun getContact(): Contact? {
        var contact = contactMutableLiveData.value
        if (contact == null) {
            contact = Contact("", "", "", "", "","")
            contactMutableLiveData.value = contact
        }
        return contactMutableLiveData.value
    }

    fun insert() {
        val disposable = ListService.saveContactList(ContactDTO(getContact()?.personEmail, getContact()?.name, user.email, getContact()?.cellPhone))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.error == null) {
                    save.call()
                } else {
                    msg.value = it.error
                }

            }, {
                error(it)
            })

        disposables.add(disposable)
    }

    fun update() {
        val disposable = ListService.updateContactList(getContact()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) {

                    if (it[0].error == null) {
                        save.call()
                    } else {
                        msg.value = it[0].error
                    }
                }
            }, {
                error(it)
            })

        disposables.add(disposable)
    }

    private fun error(throwable: Throwable) {
        error.value = throwable
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (disposables.isDisposed) disposables.clear()
    }

    fun setContactsParams(contact: Contact) {
        contactMutableLiveData.value = contact

    }

    fun setUser(user: User?) {
        this.user = user!!
    }
}
