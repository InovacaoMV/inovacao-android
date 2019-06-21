package lima.wilquer.contactlist.view.addEditar

import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.view.BaseView

interface AdicionarEditarContract {
    interface View : BaseView<Presenter> {
        fun setProgress(active: Boolean)

        fun retornoCadastrarEditar(msg: String)

        fun error(msg: String)
    }

    interface Presenter {
        fun editar(contato: Contato)

        fun cadastrar(contato: Contato)
    }
}