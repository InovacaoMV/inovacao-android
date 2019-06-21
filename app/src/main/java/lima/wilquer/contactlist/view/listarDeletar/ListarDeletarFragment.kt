package lima.wilquer.contactlist.view.listarDeletar

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_contatos.*
import kotlinx.android.synthetic.main.fragment_contatos.view.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.util.Constants
import lima.wilquer.contactlist.util.ItemDecoration
import lima.wilquer.contactlist.util.ObserverDeleteContato
import lima.wilquer.contactlist.util.RecyclerViewAdapter
import org.jetbrains.anko.support.v4.*

class ListarDeletarFragment : Fragment(), ListarDeletarContract.View, ObserverDeleteContato {

    companion object {
        fun newInstance(user: User) = ListarDeletarFragment().withArguments(Pair(Constants.USER, user))
    }

    var user: User? = null
    override lateinit var presenter: ListarDeletarContract.PresenterLD
    var rv: RecyclerView? = null
    var contatosList: MutableList<Contato> = ArrayList()
    var dialog: ProgressDialog? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_contatos, container, false)
        rv = view.recycle_contatos
        user = arguments!![Constants.USER] as User

        dialog = indeterminateProgressDialog(message = "Aguarde um momento...")
        dialog?.setCanceledOnTouchOutside(false)

        val listener = this
        rv!!.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = RecyclerViewAdapter(contatosList, ctx, listener)
            addItemDecoration(ItemDecoration(25))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        ListarDeletarPresenter(this)
        presenter.listar(user!!.email)
        contatosList.clear()
        rv!!.adapter!!.notifyDataSetChanged()
    }

    override fun setProgress(active: Boolean) {
        if (active) {
            dialog?.show()
        } else {
            dialog?.dismiss()
        }
    }

    override fun deletarContato(contato: Contato, position : Int) {
        contatosList.removeAt(position)
        rv!!.adapter!!.notifyDataSetChanged()
        longToast("${contato.name} deletado com sucesso!")
    }

    override fun listarContato(listContatos: List<Contato>) {
        contatosList.addAll(listContatos)
        rv!!.adapter!!.notifyDataSetChanged()
    }

    override fun deletarPosicao(position: Int) {
        presenter.deletar(contatosList[position]._id, position)
    }

    override fun error(msg: String) {
        longToast(msg)
    }
}