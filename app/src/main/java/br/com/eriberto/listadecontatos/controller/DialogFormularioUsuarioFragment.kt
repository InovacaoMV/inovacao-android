package br.com.eriberto.listadecontatos.controller

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import br.com.eriberto.listadecontatos.R
import br.com.eriberto.listadecontatos.model.interfaces.InteracaoPerfil
import br.com.eriberto.listadecontatos.model.modelo.Usuario
import kotlinx.android.synthetic.main.dialog_formulario_usuario.view.*

class DialogFormularioUsuarioFragment() : DialogFragment() {

    lateinit var usuario: Usuario
    lateinit var interacaoPerfil: InteracaoPerfil

    @SuppressLint("ValidFragment")
    constructor(usuario: Usuario, interacaoPerfil: InteracaoPerfil) : this() {
        this.usuario = usuario
        this.interacaoPerfil = interacaoPerfil
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_formulario_usuario, null)

        view.etEmailUsuario.setText(usuario.email)
        view.etSenhaUsuario.setText(usuario.password)

        val alert = AlertDialog.Builder(view.context)
        alert.setView(view)

        alert.setCancelable(false)

        alert.setNegativeButton(R.string.deletar) { dialog, which ->
            interacaoPerfil.deletarPerfil(usuario)
        }

        alert.setPositiveButton(R.string.salvar) { dialog, which ->

            if(view.etSenhaUsuario.text.isNullOrEmpty()){
                view.etSenhaUsuario.error = getString(R.string.invalid_password)
                return@setPositiveButton
            }

            interacaoPerfil.atualizarPerfil(
                Usuario(
                    email = view.etEmailUsuario.text.toString(),
                    password = view.etSenhaUsuario.text.toString(),
                    _id = usuario._id
                )
            )

        }

        alert.setNeutralButton(R.string.cancelar) { dialog, which ->

        }

        return alert.create()
    }


}