package com.example.desafiomv.ui.user.signIn

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.example.desafiomv.model.User
import com.example.desafiomv.model.UserDTO
import com.example.desafiomv.service.UserService
import com.example.desafiomv.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignInViewModel : ViewModel(), LifecycleObserver {

    private var userMutableLiveData = MutableLiveData<User>()
    var error = SingleLiveEvent<Throwable>()
    var msg = SingleLiveEvent<String>()
    private var disposables: CompositeDisposable = CompositeDisposable()
    private var sharedPreferences: SharedPreferences? = null
    var createAccount = SingleLiveEvent<User>()
    var deleteAccount = SingleLiveEvent<Void>()

    fun setUserParams(user: User) {
        userMutableLiveData.value = user
    }

    fun setPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    fun getUser(): User? {
        var user = userMutableLiveData.value
        if (user == null) {
            user = User("", "", "", "")
            userMutableLiveData.value = user
        }
        return userMutableLiveData.value
    }

    fun getUserRequest(email: String) {
        val disposable = UserService.getUser(email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) {

                    if (it[0].error == null) {
                        userMutableLiveData.value = it[0]
                    } else {
                        msg.value = it[0].error
                    }
                }
            }, {
                error(it)
            })

        disposables.add(disposable)

    }

    fun loginUser() {
        val disposable = UserService.login(userMutableLiveData.value?.email, userMutableLiveData.value?.password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) {
                    if (it[0].error == null) {
                        createAccount.postValue(it[0])
                    } else {
                        msg.value = it[0].error

                    }
                } else {
                    msg.value = "Error ao logar"
                }

            }, {
                error(it)
            })

        disposables.add(disposable)
    }

    fun insert() {
        val disposable = UserService.insert(UserDTO(getUser()?.email, getUser()?.password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    if (it.error == null) {
                        createAccount.postValue(it)
                    } else {
                        msg.value = it.error
                    }

            }, {
                error(it)
            })

        disposables.add(disposable)
    }

    fun update() {
        val disposable = UserService.update(getUser()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty()) {

                    if (it[0].error == null) {
                        createAccount.postValue(it[0])
                    } else {
                        msg.value = it[0].error
                    }
                }
            }, {
                error(it)
            })

        disposables.add(disposable)
    }

    fun deleteUser() {
        val disposable = UserService.deleteUser(getUser()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.error == null) {
                    deleteAccount.call()
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
