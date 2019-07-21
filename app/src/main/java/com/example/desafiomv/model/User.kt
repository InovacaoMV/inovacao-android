package com.example.desafiomv.model

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("_id")
    val id: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("error")
    var error: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}