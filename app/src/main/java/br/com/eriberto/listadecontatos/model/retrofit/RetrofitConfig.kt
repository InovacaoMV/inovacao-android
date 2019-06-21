package br.com.eriberto.listadecontatos.model.retrofit

import br.com.eriberto.listadecontatos.model.retrofit.service.ContatoService
import br.com.eriberto.listadecontatos.model.retrofit.service.UsuarioService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {

    var interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    var client = OkHttpClient.Builder().addInterceptor(interceptor).build()!!

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://inovacao-teste.herokuapp.com/")
            .client(client)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun usuarioService() = retrofit.create(UsuarioService::class.java)
    fun contatoService() = retrofit.create(ContatoService::class.java)
    /*fun loginService() = retrofit.create(LoginService::class.java)
    fun grupoService() = retrofit.create(GrupoService::class.java)
    fun produtoService() = retrofit.create(ProdutoService::class.java)
    fun formaDePagamentoService() = retrofit.create(FormaDePagamentoService::class.java)*/
}