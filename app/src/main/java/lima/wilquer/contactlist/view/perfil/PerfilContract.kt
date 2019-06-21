package lima.wilquer.contactlist.view.perfil

import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.view.BaseView

interface PerfilContract {

    interface View : BaseView<Presenter>{

        fun setProgress(active: Boolean, flagButton : Int)

        fun deleteUser(_id: String)

        fun atualizarUser(user: User?)

        fun error(msg : String)
    }

    interface Presenter {

        fun delete(_id: String)

        fun atualizar(_id: String, email: String, senha: String)
    }
}