package br.com.eriberto.listadecontatos.model.retrofit.service

import br.com.eriberto.listadecontatos.model.modelo.Contato
import retrofit2.Call
import retrofit2.http.*

interface ContatoService {

    @HTTP(method = "DELETE", path = "/api/contact-list", hasBody = true)
    fun deletarContato(
        @Body contato: Contato
    ): Call<Contato>

    @POST("/api/contact-list")
    fun cadastrarContato(
        @Body contato: Contato
    ): Call<Contato>

    @PUT("/api/contact-list")
    fun atualizarContato(
        @Body contato: Contato
    ): Call<ArrayList<Contato>>

    @GET("/api/contact-list/person-email/{user_email}")
    fun listarContatos(
        @Path("user_email") email: String
    ): Call<ArrayList<Contato>>
}