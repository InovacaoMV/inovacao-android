package lima.wilquer.contactlist.view.contatos

import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.view.BaseView

interface ContatosContract {

    interface View : BaseView<PresenterLD> {
        fun setProgress(active: Boolean)

        fun deletarContato(contato: Contato)

        fun listarContato(listContatos: List<Contato>)

        fun error(msg: String)
    }

    interface PresenterLD {
        fun deletar(_id: String)

        fun listar(email: String)
    }

    interface ViewAE : BaseView<PresenterAE> {
        fun setProgress(active: Boolean)

        fun retornoCadastrarEditar(msg: String)

        fun error(msg: String)
    }

    interface PresenterAE {
        fun editar(contato: Contato)

        fun cadastrar(contato: Contato)
    }
}