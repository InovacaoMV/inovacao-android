package com.example.desafiomv.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContactDTO (

    @SerializedName("person_email")
    var personEmail: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("user_email")
    var userEmail: String?,

    @SerializedName("cellPhone")
    var cellPhone: String?

): Serializable