package lima.wilquer.contactlist.view.perfil

import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.network.RetrofitApi
import lima.wilquer.contactlist.network.UserService
import lima.wilquer.contactlist.util.Constants
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilPresenter(val view : PerfilContract.View) : PerfilContract.Presenter{

    init {
        view.presenter = this
    }

    override fun delete(_id: String) {
        view.setProgress(true, 1)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(UserService::class.java)

            val json = JSONObject()
            json.put("_id", _id)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

            val call = apiService.deletarUser(requestBody)
            call.enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    view.setProgress(false, 1)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    view.setProgress(false, 1)
                    view.deleteUser(response.body()!!._id)
                }

            })
        }
    }

    override fun atualizar(_id: String, email: String, senha: String) {
        view.setProgress(true, 2)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(UserService::class.java)

            val json = JSONObject()
            json.put("_id", _id)
            json.put("email", email)
            json.put("password", senha)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())

            val call = apiService.atualizarUser(requestBody)
            call.enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    view.setProgress(false ,2)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    view.setProgress(false ,2)
                    if (!response.body()!!.isEmpty()) {
                        view.atualizarUser(response.body()!![0])
                    } else {
                        val jsonError = JSONObject(response.errorBody()!!.string())
                        view.error(jsonError.getString("error"))
                    }
                }

            })
        }
    }

}