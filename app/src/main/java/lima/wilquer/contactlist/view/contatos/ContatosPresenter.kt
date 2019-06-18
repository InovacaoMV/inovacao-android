package lima.wilquer.contactlist.view.contatos

import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.data.User
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

class ContatosPresenter(val view : ContatosContract.View) : ContatosContract.Presenter {


    init {
        view.presenter = this
    }

    override fun listar(email : String) {
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
                    view.listarContato(response.body()!!)
                }

            })
        }
    }

    override fun deletar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}