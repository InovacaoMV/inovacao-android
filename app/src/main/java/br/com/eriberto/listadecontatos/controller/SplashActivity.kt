package br.com.eriberto.listadecontatos.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import br.com.eriberto.listadecontatos.R
import br.com.eriberto.listadecontatos.model.modelo.Usuario
import br.com.eriberto.listadecontatos.model.retrofit.response.CallbackResponse
import br.com.eriberto.listadecontatos.model.retrofit.webClient.UsuarioWebClient
import br.com.eriberto.listadecontatos.model.room.DAOUsuario
import br.com.eriberto.listadecontatos.model.room.DatabaseUtil
import br.com.eriberto.listadecontatos.model.util.NetworkUtil

class SplashActivity : AppCompatActivity() {

    lateinit var usuarioDAO: DAOUsuario
    var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        usuarioDAO = DatabaseUtil.openConnectionDatabase(this).daoUsuario()

        usuario = usuarioDAO.getUsuario()

        if (usuario != null) {
            efetuarLogin(usuario!!.email, usuario!!.password)
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    fun efetuarLogin(email: String, senha: String) {
        if (NetworkUtil.isConnectedNetwork(this)) {

            UsuarioWebClient().login(
                email = email,
                senha = senha,
                callbackResponse = object : CallbackResponse<ArrayList<Usuario>> {
                    override fun success(response: ArrayList<Usuario>) {

                        val usuarioList: ArrayList<Usuario> = response

                        val intent = Intent(this@SplashActivity, ListaDeContatosActivity::class.java)
                        intent.putExtra("usuario", usuarioList[0])
                        startActivity(intent)
                        finish()

                    }

                    override fun failure(response: String?) {

                        if (!response.isNullOrEmpty())
                            AlertDialog.Builder(this@SplashActivity)
                                .setMessage(response)
                                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                                    efetuarLogin(usuario!!.email, usuario!!.password)
                                }
                                .setNeutralButton(R.string.tentar_outra_hora) { dialog, which ->
                                    finish()
                                }
                                .setNegativeButton(R.string.logar_novamente) { dialog, which ->
                                    usuarioDAO.delete(usuario!!)
                                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                                    finish()
                                }
                                .setCancelable(false)
                                .show()

                    }
                })

        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.titulo_sem_conexao)
                .setMessage(R.string.mensagem_sem_internet)
                .setPositiveButton(R.string.tentar_novamente) { dialog, which ->
                    efetuarLogin(usuario!!.email, usuario!!.password)
                }
                .setNeutralButton(R.string.tentar_outra_hora) { dialog, which ->
                    finish()
                }
                .show()
        }
    }
}
