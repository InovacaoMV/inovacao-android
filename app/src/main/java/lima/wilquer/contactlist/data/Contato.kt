package lima.wilquer.contactlist.data

import java.io.Serializable

data class Contato (
    val _id: String,
    val person_email: String,
    val name: String,
    val user_email: String,
    val cellphone: String,
    val __v: String
) : Serializable