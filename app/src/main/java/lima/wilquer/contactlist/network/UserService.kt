package lima.wilquer.contactlist.network

import lima.wilquer.contactlist.data.User
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("user/email/{email}")
    fun getUser(@Path("email") email: String): Call<List<User>>

    @GET("user/email/{email}/password/{password}")
    fun getLogin(@Path(encoded = false, value = "email") email: String, @Path("password") password: String): Call<List<User>>

    @POST("user")
    fun cadastrarUser(@Body requestBody: RequestBody): Call<User>

    @HTTP(method = "DELETE", path = "user", hasBody = true)
    fun deletarUser(@Body requestBody: RequestBody): Call<User>

    @PUT("user")
    fun atualizarUser(@Body requestBody: RequestBody) : Call<List<User>>
}