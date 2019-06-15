package lima.wilquer.contactlist.view.user

import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.network.RetrofitApi
import lima.wilquer.contactlist.network.UserService
import lima.wilquer.contactlist.util.Constants
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserPresenter(val view: UserContract.View) : UserContract.Presenter {

    init {
        view.presenter = this
    }

    override fun login(email: String, senha: String) {
        //view.setProgress(true)

        doAsync {
            val apiService = RetrofitApi(Constants.URL_USER).client.create(UserService::class.java)

            val call = apiService.getLogin(email,senha)
            call.enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    //view.setProgress(false)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    //view.setProgress(false)
                    view.login(response.body()!![0])
                }

            })
        }
    }

    override fun buscar(email: String) {
        doAsync {
            val apiService = RetrofitApi(Constants.URL_USER).client.create(UserService::class.java)

            val call = apiService.getUser(email)
            call.enqueue(object : Callback<List<User>> {
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    view.setProgress(false)
                    view.error(t.message.toString())
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    view.setProgress(false)
                    view.buscar(response.body()!![0])
                }

            })
        }
    }

    override fun cadastrar(email: String, senha: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(_id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun atualizar(_id: String, email: String, senha: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}