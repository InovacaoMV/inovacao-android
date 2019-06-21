package br.com.eriberto.listadecontatos.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Patterns
import android.view.View
import br.com.eriberto.listadecontatos.R
import br.com.eriberto.listadecontatos.model.enums.TIPO_ACESSO
import br.com.eriberto.listadecontatos.model.modelo.Usuario
import br.com.eriberto.listadecontatos.model.retrofit.response.CallbackResponse
import br.com.eriberto.listadecontatos.model.retrofit.webClient.UsuarioWebClient
import br.com.eriberto.listadecontatos.model.room.DAOUsuario
import br.com.eriberto.listadecontatos.model.room.DatabaseUtil
import br.com.eriberto.listadecontatos.model.util.AlertUtil
import br.com.eriberto.listadecontatos.model.util.NetworkUtil
import kotlinx.android.synthetic.main.activity_login_cadastro.*

class LoginCadastroActivity : AppCompatActivity() {
    lateinit var tipoAcesso: TIPO_ACESSO
    lateinit var usuarioDAO: DAOUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cadastro)

        usuarioDAO = DatabaseUtil.openConnectionDatabase(this).daoUsuario()

        if (intent.hasExtra("tipoAcesso")) {
            tipoAcesso = intent.extras!!["tipoAcesso"] as TIPO_ACESSO
            btLogin.text = tipoAcesso.name
        }

        btLogin.setOnClickListener {
            if (!isUserNameValid(etUsername.text.toString())) {
                etUsername.error = getString(R.string.invalid_username)
                return@setOnClickListener
            } else if (etPassword.text.isNullOrEmpty()) {
                etPassword.error = getString(R.string.invalid_password)
            } else {
                if (tipoAcesso == TIPO_ACESSO.ENTRAR)
                    efetuarLogin(email = etUsername.text.toString(), senha = etPassword.text.toString())
                else if (tipoAcesso == TIPO_ACESSO.CADASTRAR)
                    cadastrarUsuario(email = etUsername.text.toString(), senha = etPassword.text.toString())
            }
        }
    }

    fun efetuarLogin(email: String, senha: String) {
        if (NetworkUtil.isConnectedNetwork(this)) {
            loading.visibility = View.VISIBLE
            UsuarioWebClient().login(
                email = email,
                senha = senha,
                callbackResponse = object : CallbackResponse<ArrayList<Usuario>> {
                    override fun success(response: ArrayList<Usuario>) {
                        loading.visibility = View.INVISIBLE

                        val usuarioList: ArrayList<Usuario> = response

                        val usuario = usuarioDAO.getUsuario()
                        if (usuario != null)
                            usuarioDAO.delete(usuario)

                        if (usuarioList.isEmpty()) {
                            AlertUtil.showDialogGenerico(
                                context = this@LoginCadastroActivity,
                                titulo = "Falhou",
                                mensagem = "Email ou senha invalidos",
                                fecharTela = false,
                                icon = null
                            )
                        } else {
                            usuarioDAO.add(usuarioList[0])

                            val intent = Intent(this@LoginCadastroActivity, ListaDeContatosActivity::class.java)
                            intent.putExtra("usuario", usuarioList[0])
                            startActivity(intent)
                            finish()
                        }
                    }

                    override fun failure(response: String?) {
                        loading.visibility = View.INVISIBLE
                        if (!response.isNullOrEmpty())
                            AlertUtil.showToastLong(context = this@LoginCadastroActivity, texto = response)
                    }
                })

        } else {
            loading.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    efetuarLogin(email = etUsername.text.toString(), senha = etPassword.text.toString())
                }
                .setNeutralButton(R.string.cancelar) { dialog, which ->

                }
                .show()
        }
    }

    fun cadastrarUsuario(email: String, senha: String) {
        if (NetworkUtil.isConnectedNetwork(this)) {
            loading.visibility = View.VISIBLE
            UsuarioWebClient().cadastrarUsuario(
                usuario = Usuario(email, senha),
                callbackResponse = object : CallbackResponse<Usuario> {
                    override fun success(response: Usuario) {
                        loading.visibility = View.INVISIBLE

                        val usuario: Usuario = response

                        val usuarioDB = usuarioDAO.getUsuario()
                        if (usuarioDB != null)
                            usuarioDAO.delete(usuario)
                        usuarioDAO.add(usuario)

                        AlertUtil.showToastLong(
                            this@LoginCadastroActivity,
                            "Usuario ${usuario.email} cadastrado com sucesso"
                        )
                        val intent = Intent(this@LoginCadastroActivity, ListaDeContatosActivity::class.java)
                        intent.putExtra("usuario", usuario)
                        startActivity(intent)
                        finish()

                    }

                    override fun failure(response: String?) {
                        loading.visibility = View.INVISIBLE
                        if (!response.isNullOrEmpty())
                            AlertUtil.showToastLong(context = this@LoginCadastroActivity, texto = response)
                    }
                })

        } else {
            loading.visibility = View.INVISIBLE
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    cadastrarUsuario(email = etUsername.text.toString(), senha = etPassword.text.toString())
                }
                .setNeutralButton(R.string.cancelar) { dialog, which ->

                }
                .show()
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }
}
