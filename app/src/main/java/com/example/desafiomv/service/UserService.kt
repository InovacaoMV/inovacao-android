package com.example.desafiomv.service

import com.example.desafiomv.config.RetrofitConfig
import com.example.desafiomv.model.User
import com.example.desafiomv.model.UserDTO
import io.reactivex.Single

object UserService {

    fun getUser (email: String?) : Single<List<User>> {
        return RetrofitConfig.instance().getUser(email!!)
    }

    fun deleteUser (user: User?) : Single<User> {
        return RetrofitConfig.instance().deleteUser(user!!)
    }

    fun login (email: String?, password: String?) : Single<List<User>> {
        return RetrofitConfig.instance().login(email!!, password!!)
    }

    fun insert (user: UserDTO) : Single<User> {
        return RetrofitConfig.instance().createAccount(user)
    }

    fun update (user: User) : Single<List<User>> {
        return RetrofitConfig.instance().updateUser(user)
    }
}