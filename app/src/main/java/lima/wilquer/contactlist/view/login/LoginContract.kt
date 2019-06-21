package lima.wilquer.contactlist.view.login

import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.view.BaseView

interface LoginContract {

    interface View : BaseView<Presenter> {
        fun setProgress(active: Boolean, flagButton : Int)

        fun loginUser(user: User?)

        fun buscarUser(user: User?)

        fun cadastrarUser(user: User?)

        fun error(msg: String)
    }

    interface Presenter {
        fun login(email: String, senha: String)

        fun buscar(email: String)

        fun cadastrar(email: String, senha: String)
    }
}