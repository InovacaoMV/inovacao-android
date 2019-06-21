package lima.wilquer.contactlist.view.listarDeletar

import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.view.BaseView

interface ListarDeletarContract {

    interface View : BaseView<PresenterLD> {
        fun setProgress(active: Boolean)

        fun deletarContato(contato: Contato, position : Int)

        fun listarContato(listContatos: List<Contato>)

        fun error(msg: String)
    }

    interface PresenterLD {
        fun deletar(_id: String, position : Int)

        fun listar(email: String)
    }
}