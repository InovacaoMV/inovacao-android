package com.example.desafiomv.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (

    @SerializedName("_id")
    var _id: String?,

    @SerializedName("email")
    var email: String?,

    @SerializedName("password")
    var password: String?,

    @SerializedName("error")
    var error: String?
): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (_id != other._id) return false

        return true
    }

    override fun hashCode(): Int {
        return _id.hashCode()
    }
}