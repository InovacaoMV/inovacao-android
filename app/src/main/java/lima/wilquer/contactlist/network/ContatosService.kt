package lima.wilquer.contactlist.network

import lima.wilquer.contactlist.data.Contato
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContatosService {

    @POST("contact-list")
    fun cadastrarContato(@Body requestBody: RequestBody) : Call<Contato>

    @GET("contact-list/person-email/{email}")
    fun getContatos(@Path("email") email: String) : Call<List<Contato>>

}