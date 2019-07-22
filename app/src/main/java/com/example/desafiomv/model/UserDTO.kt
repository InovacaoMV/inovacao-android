package com.example.desafiomv.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserDTO (

    @SerializedName("email")
    var email: String?,

    @SerializedName("password")
    var password: String?

): Serializable