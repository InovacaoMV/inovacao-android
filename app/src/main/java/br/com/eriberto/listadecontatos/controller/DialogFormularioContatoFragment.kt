package br.com.eriberto.listadecontatos.controller

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import br.com.eriberto.listadecontatos.R
import br.com.eriberto.listadecontatos.model.interfaces.InteracaoListaContatos
import br.com.eriberto.listadecontatos.model.modelo.Contato
import br.com.eriberto.listadecontatos.model.util.MaskEditUtil
import kotlinx.android.synthetic.main.dialog_formulario_contato.view.*

class DialogFormularioContatoFragment() : DialogFragment() {

    lateinit var contato: Contato
    lateinit var interacaoListaContatos: InteracaoListaContatos

    @SuppressLint("ValidFragment")
    constructor(contato: Contato, interacaoListaContatos: InteracaoListaContatos) : this() {
        this.contato = contato
        this.interacaoListaContatos = interacaoListaContatos
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view: View = activity!!.layoutInflater.inflate(R.layout.dialog_formulario_contato, null)

        if (contato._id.isNullOrEmpty())
            view.tvTitulo.text = getString(R.string.cadastro_contato)
        else
            view.tvTitulo.text = getString(R.string.editar_contato)

        view.etNomeContato.setText(contato.name)
        view.etEmailContato.setText(contato.person_email)
        view.etTelefoneConato.addTextChangedListener(
            MaskEditUtil.mask(
                view.etTelefoneConato,
                MaskEditUtil.FORMAT_CELULAR
            )
        )
        view.etTelefoneConato.setText(contato.cellphone)

        val alert = AlertDialog.Builder(view.context)
        alert.setView(view)

        if (!contato._id.isNullOrEmpty())
            alert.setNegativeButton(R.string.deletar) { dialog, which ->
                interacaoListaContatos.deletarContato(contato)
            }

        alert.setPositiveButton(R.string.salvar) { dialog, which ->
            if (contato._id.isNullOrEmpty()) {
                interacaoListaContatos.cadastrarContato(
                    Contato(
                        name = view.etNomeContato.text.toString(),
                        user_email = contato.user_email,
                        cellphone = view.etTelefoneConato.text.toString(),
                        person_email = view.etEmailContato.text.toString()
                    )
                )
            } else {
                interacaoListaContatos.atualizarContato(
                    Contato(
                        name = view.etNomeContato.text.toString(),
                        user_email = contato.user_email,
                        cellphone = view.etTelefoneConato.text.toString(),
                        person_email = view.etEmailContato.text.toString(),
                        _id = contato._id!!
                    )
                )
            }
        }

        alert.setNeutralButton(R.string.cancelar) { dialog, which ->

        }

        return alert.create()
    }
}