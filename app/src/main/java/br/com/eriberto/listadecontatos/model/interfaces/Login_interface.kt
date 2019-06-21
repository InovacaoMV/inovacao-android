package br.com.eriberto.listadecontatos.model.interfaces

interface Login_interface {
    fun loginOk()
    fun loginFalhou()
    fun usuarioInvalido()
    fun senhaInvalida()
}