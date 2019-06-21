package br.com.eriberto.listadecontatos.controller

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.com.eriberto.listadecontatos.R
import br.com.eriberto.listadecontatos.model.interfaces.InteracaoListaContatos
import br.com.eriberto.listadecontatos.model.interfaces.InteracaoPerfil
import br.com.eriberto.listadecontatos.model.modelo.Contato
import br.com.eriberto.listadecontatos.model.modelo.Usuario
import br.com.eriberto.listadecontatos.model.recyclerViewAdapter.ContatoRecyclerViewAdapter
import br.com.eriberto.listadecontatos.model.retrofit.response.CallbackResponse
import br.com.eriberto.listadecontatos.model.retrofit.webClient.ContatoWebClient
import br.com.eriberto.listadecontatos.model.retrofit.webClient.UsuarioWebClient
import br.com.eriberto.listadecontatos.model.room.DAOUsuario
import br.com.eriberto.listadecontatos.model.room.DatabaseUtil
import br.com.eriberto.listadecontatos.model.util.AlertUtil
import br.com.eriberto.listadecontatos.model.util.NetworkUtil

import kotlinx.android.synthetic.main.activity_lista_de_contatos.*
import kotlinx.android.synthetic.main.content_lista_de_contatos.*

class ListaDeContatosActivity : AppCompatActivity(), InteracaoListaContatos, InteracaoPerfil {
    lateinit var usuarioRecebido: Usuario
    lateinit var contatoSelecionado: Contato

    lateinit var usuarioDAO: DAOUsuario
    var dialogFormularioContatoFragment = DialogFormularioContatoFragment()
    var dialogFormularioUsuarioFragment = DialogFormularioUsuarioFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_de_contatos)
        setSupportActionBar(toolbar)

        usuarioDAO = DatabaseUtil.openConnectionDatabase(this).daoUsuario()

        usuarioRecebido = intent.extras!!["usuario"] as Usuario

        recyclerView_contatos.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { _ ->
            dialogFormularioContatoFragment =
                DialogFormularioContatoFragment(
                    contato = Contato(
                        usuarioRecebido.email
                    ), interacaoListaContatos = this
                )
            dialogFormularioContatoFragment.show(supportFragmentManager, "")
        }

        getConatatos(usuarioRecebido.email)
    }

    override fun atualizarPerfil(usuario: Usuario) {
        atualizarUsuario(usuario)
    }

    override fun deletarPerfil(usuario: Usuario) {
        deletarUsuario(usuario)
    }

    override fun selecionouContato(contato: Contato) {
        contatoSelecionado = contato
        dialogFormularioContatoFragment =
            DialogFormularioContatoFragment(
                contato = contato,
                interacaoListaContatos = this
            )
        dialogFormularioContatoFragment.show(supportFragmentManager, "")
    }

    override fun cadastrarContato(contato: Contato) {
        contatoSelecionado = contato
        cadastrar(contato)
        dialogFormularioContatoFragment.dismiss()

    }

    override fun atualizarContato(contato: Contato) {
        contatoSelecionado = contato
        atualizar(contato)
        dialogFormularioContatoFragment.dismiss()

    }

    override fun deletarContato(contato: Contato) {
        contatoSelecionado = contato
        deletar(contato)
        dialogFormularioContatoFragment.dismiss()

    }

    fun getConatatos(email: String) {
        if (NetworkUtil.isConnectedNetwork(this)) {
            progressBarListaDeContatos.visibility = View.VISIBLE
            ContatoWebClient().listarContatos(
                email = email,
                callbackResponse = object : CallbackResponse<ArrayList<Contato>> {
                    override fun success(response: ArrayList<Contato>) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE

                        val contatoList: ArrayList<Contato> = response
                        if (contatoList.isEmpty())
                            tvListaVazia.visibility = View.VISIBLE
                        else
                            tvListaVazia.visibility = View.GONE

                        recyclerView_contatos.adapter =
                            ContatoRecyclerViewAdapter(
                                mValues = contatoList,
                                interacaoListaContatos = this@ListaDeContatosActivity
                            )
                    }

                    override fun failure(response: String?) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE
                        if (!response.isNullOrEmpty())
                            AlertUtil.showDialogGenerico(
                                context = this@ListaDeContatosActivity,
                                icon = null,
                                fecharTela = false,
                                mensagem = response,
                                titulo = null
                            )
                    }
                })

        } else {
            progressBarListaDeContatos.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    getConatatos(usuarioRecebido.email)
                }
                .setNeutralButton(R.string.cancelar) { dialog, which ->

                }
                .show()
        }
    }

    fun cadastrar(contato: Contato) {
        if (NetworkUtil.isConnectedNetwork(this)) {
            progressBarListaDeContatos.visibility = View.VISIBLE
            ContatoWebClient().cadastrarContato(
                contato = contato,
                callbackResponse = object : CallbackResponse<Contato> {
                    override fun success(response: Contato) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE
                        AlertUtil.showToastLong(
                            this@ListaDeContatosActivity,
                            getString(R.string.cadastrado_com_sucesso)
                        )
                        contatoSelecionado = response
                        getConatatos(usuarioRecebido.email)
                    }

                    override fun failure(response: String?) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE
                        if (!response.isNullOrEmpty())
                            AlertUtil.showDialogGenerico(
                                context = this@ListaDeContatosActivity,
                                icon = null,
                                fecharTela = false,
                                mensagem = response,
                                titulo = null
                            )
                    }
                })

        } else {
            progressBarListaDeContatos.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    cadastrar(contatoSelecionado)
                }
                .setNeutralButton(R.string.cancelar) { dialog, which ->

                }
                .show()
        }
    }

    fun atualizar(contato: Contato) {
        if (NetworkUtil.isConnectedNetwork(this)) {
            progressBarListaDeContatos.visibility = View.VISIBLE
            ContatoWebClient().atualizarContato(
                contato = contato,
                callbackResponse = object : CallbackResponse<ArrayList<Contato>> {
                    override fun success(response: ArrayList<Contato>) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE

                        AlertUtil.showToastLong(
                            this@ListaDeContatosActivity,
                            getString(R.string.atualizado_com_sucesso)
                        )
                        getConatatos(usuarioRecebido.email)
                    }

                    override fun failure(response: String?) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE
                        if (!response.isNullOrEmpty())
                            AlertUtil.showDialogGenerico(
                                context = this@ListaDeContatosActivity,
                                icon = null,
                                fecharTela = false,
                                mensagem = response,
                                titulo = null
                            )
                    }
                })

        } else {
            progressBarListaDeContatos.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    atualizar(contatoSelecionado)
                }
                .setNeutralButton(R.string.cancelar) { dialog, which ->

                }
                .show()
        }
    }

    fun deletar(contato: Contato) {
        if (NetworkUtil.isConnectedNetwork(this)) {
            progressBarListaDeContatos.visibility = View.VISIBLE
            ContatoWebClient().deletarContato(
                contato = contato,
                callbackResponse = object : CallbackResponse<Contato> {
                    override fun success(response: Contato) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE

                        AlertUtil.showToastLong(this@ListaDeContatosActivity, getString(R.string.deletado_com_sucesso))
                        getConatatos(usuarioRecebido.email)
                    }

                    override fun failure(response: String?) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE
                        if (!response.isNullOrEmpty())
                            AlertUtil.showDialogGenerico(
                                context = this@ListaDeContatosActivity,
                                icon = null,
                                fecharTela = false,
                                mensagem = response,
                                titulo = null
                            )
                    }
                })

        } else {
            progressBarListaDeContatos.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    cadastrar(contatoSelecionado)
                }
                .setNeutralButton(R.string.cancelar) { dialog, which ->

                }
                .show()
        }
    }

    fun atualizarUsuario(usuario: Usuario) {
        if (NetworkUtil.isConnectedNetwork(this)) {
            progressBarListaDeContatos.visibility = View.VISIBLE
            UsuarioWebClient().atualizarUsuario(
                usuario = usuario,
                callbackResponse = object : CallbackResponse<ArrayList<Usuario>> {
                    override fun success(response: ArrayList<Usuario>) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE

                        val usuarioRes: Usuario = response[0]

                        val usuarioDB = usuarioDAO.getUsuario()
                        usuarioDAO.delete(usuarioDB!!)
                        usuarioDAO.add(usuarioRes)

                        usuarioRecebido = usuarioRes

                        AlertUtil.showToastLong(
                            this@ListaDeContatosActivity,
                            getString(R.string.perfil_atualizado_com_sucesso)
                        )
                    }

                    override fun failure(response: String?) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE
                        if (!response.isNullOrEmpty())
                            AlertUtil.showDialogGenerico(
                                context = this@ListaDeContatosActivity,
                                icon = null,
                                fecharTela = false,
                                mensagem = response,
                                titulo = null
                            )
                    }
                })

        } else {
            progressBarListaDeContatos.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    atualizarUsuario(usuarioRecebido)
                }
                .setNeutralButton(R.string.cancelar) { dialog, which ->

                }
                .show()
        }
    }

    fun deletarUsuario(usuario: Usuario) {
        if (NetworkUtil.isConnectedNetwork(this)) {
            progressBarListaDeContatos.visibility = View.VISIBLE
            UsuarioWebClient().deletarUsuario(
                usuario = usuario,
                callbackResponse = object : CallbackResponse<Usuario> {
                    override fun success(response: Usuario) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE

                        usuarioDAO.delete(usuarioDAO.getUsuario()!!)

                        AlertUtil.showToastShort(
                            this@ListaDeContatosActivity,
                            getString(R.string.perfil_deletado_com_sucesso)
                        )
                        startActivity(Intent(this@ListaDeContatosActivity, MainActivity::class.java))
                        finish()

                    }

                    override fun failure(response: String?) {
                        progressBarListaDeContatos.visibility = View.INVISIBLE
                        if (!response.isNullOrEmpty())
                            AlertUtil.showDialogGenerico(
                                context = this@ListaDeContatosActivity,
                                icon = null,
                                fecharTela = false,
                                mensagem = response,
                                titulo = null
                            )
                    }
                })

        } else {
            progressBarListaDeContatos.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    deletarUsuario(usuarioRecebido)
                }
                .setNeutralButton(R.string.cancelar) { dialog, which ->

                }
                .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_contatos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.btMenuPerfil -> {
                dialogFormularioUsuarioFragment =
                    DialogFormularioUsuarioFragment(
                        usuario = usuarioRecebido,
                        interacaoPerfil = this
                    )
                dialogFormularioUsuarioFragment.show(supportFragmentManager, "")
                true
            }
            R.id.btMenuSair -> {

                usuarioDAO.delete(usuarioDAO.getUsuario()!!)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
