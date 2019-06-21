package br.com.eriberto.listadecontatos.model.retrofit.response

interface CallbackResponse<T> {
    fun success(response: T)

    fun failure(response: String?)
}