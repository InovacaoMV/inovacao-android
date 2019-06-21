package br.com.eriberto.listadecontatos.model.retrofit.webClient

import br.com.eriberto.listadecontatos.model.modelo.Contato
import br.com.eriberto.listadecontatos.model.modelo.ErroReportado
import br.com.eriberto.listadecontatos.model.retrofit.RetrofitConfig
import br.com.eriberto.listadecontatos.model.retrofit.response.CallbackResponse
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContatoWebClient {
    fun listarContatos(email: String, callbackResponse: CallbackResponse<ArrayList<Contato>>) {
        val call = RetrofitConfig().contatoService().listarContatos(email = email)

        call.enqueue(object : Callback<ArrayList<Contato>> {
            override fun onResponse(call: Call<ArrayList<Contato>>, response: Response<ArrayList<Contato>>) {

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

            override fun onFailure(call: Call<ArrayList<Contato>>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }
        })
    }

    fun atualizarContato(contato: Contato, callbackResponse: CallbackResponse<ArrayList<Contato>>) {
        val call = RetrofitConfig().contatoService().atualizarContato(contato = contato)

        call.enqueue(object : Callback<ArrayList<Contato>> {
            override fun onResponse(call: Call<ArrayList<Contato>>, response: Response<ArrayList<Contato>>) {

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

            override fun onFailure(call: Call<ArrayList<Contato>>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }
        })
    }

    fun deletarContato(contato: Contato, callbackResponse: CallbackResponse<Contato>) {
        val call = RetrofitConfig().contatoService().deletarContato(contato)

        call.enqueue(object : Callback<Contato> {
            override fun onResponse(call: Call<Contato>, response: Response<Contato>) {

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

            override fun onFailure(call: Call<Contato>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }
        })
    }

    fun cadastrarContato(contato: Contato, callbackResponse: CallbackResponse<Contato>) {
        val call = RetrofitConfig().contatoService().cadastrarContato(contato = contato)

        call.enqueue(object : Callback<Contato> {
            override fun onResponse(call: Call<Contato>, response: Response<Contato>) {

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

            override fun onFailure(call: Call<Contato>, t: Throwable) {
                callbackResponse.failure(t.message.toString())
            }
        })
    }

}
