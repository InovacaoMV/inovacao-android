package lima.wilquer.contactlist.view.login

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

class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {

    init {
        view.presenter = this
    }

    override fun login(email: String, senha: String) {
        view.setProgress(true,1)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(UserService::class.java)

            val call = apiService.getLogin(email, senha)
            call.enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    view.setProgress(false, 1)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    view.setProgress(false,1)
                    if (!response.body()!!.isEmpty()) {
                        view.loginUser(response.body()!![0])
                    } else {
                        view.error("Não existe usuario com este login/senha.")
                    }
                }

            })
        }
    }

    override fun buscar(email: String) {
        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(UserService::class.java)

            val call = apiService.getUser(email)
            call.enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    view.setProgress(false,0)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    view.setProgress(false,0)
                    if (!response.body()!!.isEmpty()) {
                        view.buscarUser(response.body()!![0])
                    } else {
                        view.error("Nenhum usuário encontrado.")
                    }
                }

            })
        }
    }

    override fun cadastrar(email: String, senha: String) {
        view.setProgress(true,2)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_GERAL).client.create(UserService::class.java)

            val json = JSONObject()
            json.put("email", email)
            json.put("password", senha)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json.toString())
            val call = apiService.cadastrarUser(requestBody)
            call.enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    view.setProgress(false,2)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    view.setProgress(false,2)
                    if (response.isSuccessful) {
                        view.cadastrarUser(response.body())
                    } else {
                        val jsonError = JSONObject(response.errorBody()!!.string())
                        view.error(jsonError.getString("error"))
                    }
                }

            })
        }
    }
}