package br.com.eriberto.listadecontatos.model.interfaces

import br.com.eriberto.listadecontatos.model.modelo.Contato

interface InteracaoListaContatos {

    fun selecionouContato(contato: Contato)

    fun cadastrarContato(contato: Contato)

    fun atualizarContato(contato: Contato)

    fun deletarContato(contato: Contato)
}