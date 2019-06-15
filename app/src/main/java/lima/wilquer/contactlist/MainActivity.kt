package lima.wilquer.contactlist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.network.RetrofitApi
import lima.wilquer.contactlist.network.UserService
import lima.wilquer.contactlist.util.Constants
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_login -> login()
            R.id.button_cadastrar -> {}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_login.setOnClickListener(this)
        button_cadastrar.setOnClickListener(this)
    }

    fun login(){
        doAsync {
            val apiService = RetrofitApi(Constants.URL_USER).client.create(UserService::class.java)

            val call = apiService.getLogin("wtl@cin.ufpe.br","123")
            call.enqueue(object : Callback<List<User>>{
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.d("ERROR   ", t.message.toString())
                }

                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    print("RESPONSE     "+response.body())
                    Log.d("SUCCESS   ", response.body().toString())
                }

            })
        }
    }
}
