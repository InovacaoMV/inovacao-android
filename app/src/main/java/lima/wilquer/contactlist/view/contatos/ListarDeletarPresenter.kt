package lima.wilquer.contactlist.view.contatos

import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.network.ContatosService
import lima.wilquer.contactlist.network.RetrofitApi
import lima.wilquer.contactlist.util.Constants
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListarDeletarPresenter(val view: ContatosContract.View) : ContatosContract.PresenterLD {


    init {
        view.presenter = this
    }

    override fun listar(email: String) {
        //view.setProgress(true)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(ContatosService::class.java)

            val call = apiService.getContatos(email)
            call.enqueue(object : Callback<List<Contato>> {
                override fun onFailure(call: Call<List<Contato>>, t: Throwable) {
                    //view.setProgress(false)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<List<Contato>>, response: Response<List<Contato>>) {
                    //view.setProgress(false)
                    if (!response.body()!!.isEmpty()) {
                        view.listarContato(response.body()!!)
                    } else {
                        view.error("Não existe contatos por enquanto")
                    }
                }

            })
        }
    }

    override fun deletar(_id: String) {
        //view.setProgress(true)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(ContatosService::class.java)

            val json = JSONObject()
            json.put("_id", _id)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

            val call = apiService.deletarContato(requestBody)
            call.enqueue(object : Callback<Contato> {
                override fun onFailure(call: Call<Contato>, t: Throwable) {
                    //view.setProgress(false)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<Contato>, response: Response<Contato>) {
                    //view.setProgress(false)
                    view.deletarContato(response.body()!!)
                }

            })
        }
    }
}