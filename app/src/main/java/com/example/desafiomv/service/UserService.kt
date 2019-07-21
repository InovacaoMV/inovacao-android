package com.example.desafiomv.service

import com.example.desafiomv.config.RetrofitConfig
import com.example.desafiomv.model.User
import io.reactivex.Observable
import io.reactivex.Single

object UserService {

    fun getUser (email: String) : Single<User> {
        return RetrofitConfig.instance().getUser(email)
    }

    fun deleteUser (user: User) : Single<User> {
        return RetrofitConfig.instance().deleteUser(user)
    }

    fun login (email: String, password: String) : Single<User> {
        return RetrofitConfig.instance().login(email, password)
    }

    fun registerUser (user: User) : Single<User> {
        return RetrofitConfig.instance().registerUser(user)
    }

    fun updateUser (user: User) : Single<User> {
        return RetrofitConfig.instance().updateUser(user)
    }
}