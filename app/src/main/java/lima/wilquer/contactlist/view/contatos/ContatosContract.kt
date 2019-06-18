package lima.wilquer.contactlist.view.contatos

import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.view.BaseView

interface ContatosContract {

    interface View : BaseView<Presenter> {
        fun setProgress(active: Boolean)

        fun deletarContato()

        fun listarContato(listContatos: List<Contato>)

        fun error(msg: String)
    }

    interface Presenter {
        fun deletar()

        fun listar(email: String)
    }
}