package br.com.eriberto.listadecontatos.model.modelo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class Usuario : Serializable {
    @PrimaryKey(autoGenerate = false)
    lateinit var _id: String
    lateinit var email: String
    lateinit var password: String

    constructor()

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
    constructor(_id: String, email: String, password: String) {
        this._id = _id
        this.email = email
        this.password = password
    }
}