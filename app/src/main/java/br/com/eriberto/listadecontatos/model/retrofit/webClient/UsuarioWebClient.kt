package br.com.eriberto.listadecontatos.model.retrofit.webClient

import br.com.eriberto.listadecontatos.model.modelo.ErroReportado
import br.com.eriberto.listadecontatos.model.modelo.Usuario
import br.com.eriberto.listadecontatos.model.retrofit.RetrofitConfig
import br.com.eriberto.listadecontatos.model.retrofit.response.CallbackResponse
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioWebClient {

    fun login(email: String, senha: String, callbackResponse: CallbackResponse<ArrayList<Usuario>>) {

        val call = RetrofitConfig().usuarioService().login(email = email, password = senha)

        call.enqueue(object : Callback<ArrayList<Usuario>> {
            override fun onResponse(call: Call<ArrayList<Usuario>>, response: Response<ArrayList<Usuario>>) {

                response.body()?.let {
                    callbackResponse.success(it)
                }

                response.errorBody().let {
                    if (it != null) {
                        val erroReportado: ErroReportado = Gson().fromJson(it.string(), ErroReportado::class.java)
                        callbackResponse.failure(erroReportado.error)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Usuario>>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }

        })
    }

    fun buscarUsuarioPorEmail(email: String, callbackResponse: CallbackResponse<ArrayList<Usuario>>) {
        val call = RetrofitConfig().usuarioService().buscarUsuarioPorEmail(email = email)

        call.enqueue(object : Callback<ArrayList<Usuario>> {
            override fun onResponse(call: Call<ArrayList<Usuario>>, response: Response<ArrayList<Usuario>>) {

                response.body()?.let {
                    callbackResponse.success(it)
                }

                response.errorBody().let {
                    if (it != null) {
                        val erroReportado: ErroReportado = Gson().fromJson(it.string(), ErroReportado::class.java)
                        callbackResponse.failure(erroReportado.error)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Usuario>>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }
        })
    }

    fun deletarUsuario(usuario: Usuario, callbackResponse: CallbackResponse<Usuario>) {
        val call = RetrofitConfig().usuarioService().deletarUsuario(usuario)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                response.body()?.let {
                    callbackResponse.success(it)
                }

                response.errorBody().let {
                    if (it != null) {
                        val erroReportado: ErroReportado = Gson().fromJson(it.string(), ErroReportado::class.java)
                        callbackResponse.failure(erroReportado.error)
                    }
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }
        })
    }

    fun cadastrarUsuario(usuario: Usuario, callbackResponse: CallbackResponse<Usuario>) {
        val call = RetrofitConfig().usuarioService().cadastrarUsuario(usuario = usuario)

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                response.body()?.let {
                    callbackResponse.success(it)
                }

                response.errorBody().let {
                    if (it != null) {
                        val erroReportado: ErroReportado = Gson().fromJson(it.string(), ErroReportado::class.java)
                        callbackResponse.failure(erroReportado.error)
                    }
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }
        })
    }

    fun atualizarUsuario(usuario: Usuario, callbackResponse: CallbackResponse<ArrayList<Usuario>>) {
        val call = RetrofitConfig().usuarioService().atualizarUsuario(usuario = usuario)

        call.enqueue(object : Callback<ArrayList<Usuario>> {
            override fun onResponse(call: Call<ArrayList<Usuario>>, response: Response<ArrayList<Usuario>>) {

                response.body()?.let {
                    callbackResponse.success(it)
                }

                response.errorBody().let {
                    if (it != null) {
                        val erroReportado: ErroReportado = Gson().fromJson(it.string(), ErroReportado::class.java)
                        callbackResponse.failure(erroReportado.error)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Usuario>>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }
        })
    }
}