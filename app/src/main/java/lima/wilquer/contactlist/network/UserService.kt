package lima.wilquer.contactlist.network

import lima.wilquer.contactlist.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("email/{email}?")
    fun getUser(@Path("email") email: String) : Call<List<User>>

    @GET("email/{email}?/password/")
    fun getLogin(@Path("email") email: String, @Query("password") password: String) : Call<List<User>>
}