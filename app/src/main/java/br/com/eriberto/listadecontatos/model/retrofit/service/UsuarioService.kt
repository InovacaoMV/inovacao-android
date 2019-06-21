package br.com.eriberto.listadecontatos.model.retrofit.service

import br.com.eriberto.listadecontatos.model.modelo.Usuario
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {

    @GET("/api/user/email/{email}/password/{password}")
    fun login(
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<ArrayList<Usuario>>

    @GET("/api/user/email/{email}")
    fun buscarUsuarioPorEmail(
        @Path("email") email: String
    ): Call<ArrayList<Usuario>>

    //@DELETE("/api/user")
    @HTTP(method = "DELETE", path = "/api/user", hasBody = true)
    fun deletarUsuario(
        @Body usuario: Usuario
    ): Call<Usuario>

    @POST("/api/user")
    fun cadastrarUsuario(
        @Body usuario: Usuario
    ): Call<Usuario>

    @PUT("/api/user")
    fun atualizarUsuario(
        @Body usuario: Usuario
    ): Call<ArrayList<Usuario>>
}