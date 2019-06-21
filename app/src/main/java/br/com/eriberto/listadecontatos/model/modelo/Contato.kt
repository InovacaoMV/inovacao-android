package br.com.eriberto.listadecontatos.model.modelo

class Contato {
    var _id: String? = null
    var person_email: String? = null
    var name: String? = null
    lateinit var user_email: String
    var cellphone: String? = null

    constructor(person_email: String, name: String, user_email: String, cellphone: String) {
        this.person_email = person_email
        this.name = name
        this.user_email = user_email
        this.cellphone = cellphone
    }

    constructor(_id: String, person_email: String, name: String, user_email: String, cellphone: String) {
        this._id = _id
        this.person_email = person_email
        this.name = name
        this.user_email = user_email
        this.cellphone = cellphone
    }

    constructor(user_email: String) {
        this.user_email = user_email
    }


}