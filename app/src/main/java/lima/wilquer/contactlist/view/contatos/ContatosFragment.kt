package lima.wilquer.contactlist.view.contatos

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
import lima.wilquer.contactlist.util.ObserverDeleteContato
import lima.wilquer.contactlist.util.RecyclerViewAdapter
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.withArguments

class ContatosFragment : Fragment(), ContatosContract.View, ObserverDeleteContato {

    companion object {
        fun newInstance(user: User) = ContatosFragment().withArguments(Pair(Constants.USER, user))
    }

    var user: User? = null
    override lateinit var presenter: ContatosContract.PresenterLD
    var rv: RecyclerView? = null
    var contatosList: MutableList<Contato> = ArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_contatos, container, false)
        rv = view.recycle_contatos
        user = arguments!![Constants.USER] as User

        val listener = this
        rv!!.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = RecyclerViewAdapter(contatosList, ctx, listener)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        //if (!this::presenter.isInitialized) {
        ListarDeletarPresenter(this)
        presenter.listar(user!!.email)
        contatosList.clear()
        rv!!.adapter!!.notifyDataSetChanged()
        //}
    }

    override fun setProgress(active: Boolean) {
        if(active) progress_contato.visibility = View.VISIBLE else progress_contato.visibility = View.INVISIBLE
    }

    override fun deletarContato(contato: Contato) {
        rv!!.adapter!!.notifyDataSetChanged()
        Toast.makeText(activity, contato.toString(), Toast.LENGTH_LONG).show()
    }

    override fun listarContato(listContatos: List<Contato>) {
        contatosList.addAll(listContatos)
        rv!!.adapter!!.notifyDataSetChanged()
    }

    override fun deletarPosicao(positon: Int) {
        presenter.deletar(contatosList[positon]._id)
        contatosList.removeAt(positon)
        rv!!.adapter!!.notifyItemRemoved(positon)
    }

    override fun error(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}