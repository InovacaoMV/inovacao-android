package com.example.desafiomv.ui.contacts.listContacts

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

    var contactMutableLiveData = MutableLiveData<Contact>()
    var error = SingleLiveEvent<Throwable>()
    var msg = SingleLiveEvent<String>()
    private var disposables: CompositeDisposable = CompositeDisposable()
    var delete = SingleLiveEvent<Void>()


    fun setUser(user: User) {
        getContacts(user.email!!)
    }


    fun getContacts(email: String) {
        val disposable = ListService.getContactList(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.error == null) {
                    contactMutableLiveData.value = it
                } else {
                    msg.value = it.error
                }

            }, {
                error(it)
            })

        disposables.add(disposable)
    }

    fun delete() {
        val disposable = ListService.deleteContactList(Contact("","","","","",""))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
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
        error.value = throwable
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (disposables.isDisposed) disposables.clear()
    }

}
