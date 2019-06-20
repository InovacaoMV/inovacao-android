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

class AdicionarEditarPresenter(val view: ContatosContract.ViewAE) : ContatosContract.PresenterAE {

    init {
        view.presenter = this
    }

    override fun editar(contato: Contato) {
        view.setProgress(true)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(ContatosService::class.java)

            val json = JSONObject()
            json.put("_id", contato._id)
            json.put("person_email", contato.person_email)
            json.put("name", contato.name)
            json.put("user_email", contato.user_email)
            json.put("cellphone", contato.cellphone)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

            val call = apiService.atualizarContato(requestBody)
            call.enqueue(object : Callback<List<Contato>> {
                override fun onFailure(call: Call<List<Contato>>, t: Throwable) {
                    view.setProgress(false)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<List<Contato>>, response: Response<List<Contato>>) {
                    view.setProgress(false)
                    view.retornoCadastrarEditar("Contato atualizado com sucesso!")
                }
            })
        }
    }

    override fun cadastrar(contato: Contato) {
        view.setProgress(true)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(ContatosService::class.java)

            val json = JSONObject()
            json.put("person_email", "")
            json.put("name", contato.name)
            json.put("user_email", contato.user_email)
            json.put("cellphone", contato.cellphone)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

            val call = apiService.cadastrarContato(requestBody)
            call.enqueue(object : Callback<Contato> {
                override fun onFailure(call: Call<Contato>, t: Throwable) {
                    view.setProgress(false)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<Contato>, response: Response<Contato>) {
                    view.setProgress(false)
                    view.retornoCadastrarEditar("Contato cadastrado com sucesso!")
                }

            })
        }
    }

}