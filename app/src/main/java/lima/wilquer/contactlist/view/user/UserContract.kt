package lima.wilquer.contactlist.view.user

import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.view.BaseView

interface UserContract {

    interface View : BaseView<Presenter> {
        fun setProgress(active: Boolean)

        fun login(user: User)

        fun buscar(user: User)

        fun cadastrar(email: String, senha: String)

        fun delete(_id: String)

        fun atualizar(_id: String, email: String, senha: String)

        fun error(msg: String)
    }

    interface Presenter : BaseUserPresenter
}