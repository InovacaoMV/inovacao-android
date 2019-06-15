package lima.wilquer.contactlist.view.user

interface BaseUserPresenter{
    fun login(email: String, senha: String)

    fun buscar(email: String)

    fun cadastrar(email: String, senha: String)

    fun delete(_id: String)

    fun atualizar(_id: String, email: String, senha: String)
}