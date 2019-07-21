package com.example.desafiomv.model

import com.google.gson.annotations.SerializedName

data class Contact (

    @SerializedName("_id")
    val id: String,

    @SerializedName("person_email")
    var personEmail: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("user_email")
    var userEmail: String?,

    @SerializedName("cellPhone")
    var cellPhone: String?,

    @SerializedName("error")
    val error: String?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contact) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}