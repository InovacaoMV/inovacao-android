package br.com.eriberto.listadecontatos.model.interfaces

import br.com.eriberto.listadecontatos.model.modelo.Usuario

interface InteracaoPerfil {

    fun atualizarPerfil(usuario: Usuario)

    fun deletarPerfil(usuario: Usuario)
}