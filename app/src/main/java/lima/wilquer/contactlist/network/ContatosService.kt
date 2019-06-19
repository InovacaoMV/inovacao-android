package lima.wilquer.contactlist.network

import lima.wilquer.contactlist.data.Contato
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ContatosService {

    @POST("contact-list")
    fun cadastrarContato(@Body requestBody: RequestBody) : Call<Contato>

    @GET("contact-list/person-email/{email}")
    fun getContatos(@Path("email") email: String) : Call<List<Contato>>

    @HTTP(method = "DELETE", path = "contact-list", hasBody = true)
    fun deletarContato(@Body requestBody: RequestBody): Call<Contato>

    @PUT("contact-list")
    fun atualizarContato(@Body requestBody: RequestBody) : Call<List<Contato>>
}