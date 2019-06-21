package lima.wilquer.contactlist.data

import java.io.Serializable

data class User(
    var _id: String,
    var email: String,
    var password: String
) : Serializable