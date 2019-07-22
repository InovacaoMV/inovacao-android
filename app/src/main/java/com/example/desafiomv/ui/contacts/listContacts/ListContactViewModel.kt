package com.example.desafiomv.ui.contacts.listContacts

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import com.example.desafiomv.model.Contact
import com.example.desafiomv.model.ContactDTO
import com.example.desafiomv.model.User
import com.example.desafiomv.service.ListService
import com.example.desafiomv.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListContactViewModel : ViewModel(), LifecycleObserver {

    var error = SingleLiveEvent<Throwable>()
    var msg = SingleLiveEvent<String>()
    var listContacts = SingleLiveEvent<List<Contact>>()
    var listEmpty = ObservableBoolean(false)
    private var disposables: CompositeDisposable = CompositeDisposable()
    var delete = SingleLiveEvent<Void>()
    var loading = ObservableBoolean(false)

    fun setUser(user: User) {
        getContacts(user.email!!)
    }

    fun getContacts(email: String) {
        loading.set(true)
        val disposable = ListService.getContactList(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loading.set(false)
                if (it.isNotEmpty()) {
                    listContacts.postValue(it)
                } else {
                    listEmpty.set(true)
                }

            }, {
                error(it)
            })

        disposables.add(disposable)
    }

    fun delete(contact: Contact) {
        loading.set(true)
        val disposable = ListService.deleteContactList(contact)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                loading.set(false)
                if (it.error == null) {
                    delete.call()
                } else {
                    msg.value = it.error
                }
            }, {
                error(it)
            })

        disposables.add(disposable)
    }

    private fun error(throwable: Throwable) {
        loading.set(false)
        error.value = throwable
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (disposables.isDisposed) disposables.clear()
    }

}
