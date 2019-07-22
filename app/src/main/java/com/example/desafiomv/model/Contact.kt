package com.example.desafiomv.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Contact (

    @SerializedName("_id")
    var _id: String?,

    @SerializedName("person_email")
    var personEmail: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("user_email")
    var userEmail: String?,

    @SerializedName("cellPhone")
    var cellPhone: String?,

    @SerializedName("error")
    var error: String?
):  Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Contact) return false

        if (_id != other._id) return false

        return true
    }

    override fun hashCode(): Int {
        return _id.hashCode()
    }
}