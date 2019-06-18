package lima.wilquer.contactlist.view.contatos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_contatos.view.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.util.RecyclerViewAdapter
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.withArguments

class ContatosFragment : Fragment(), ContatosContract.View{

    companion object {
        fun newInstance(user: User) = ContatosFragment().withArguments(Pair("user", user))
    }

    var user : User? = null
    override lateinit var presenter: ContatosContract.Presenter
    var rv: RecyclerView? = null
    var contatosList : MutableList<Contato>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view :View = inflater.inflate(R.layout.fragment_contatos, container, false)
        rv = view.recycle_contatos
        user = arguments!!["user"] as User
        return view
    }

    override fun onResume() {
        super.onResume()
        contatosList?.clear()
        if(!this::presenter.isInitialized){
            ContatosPresenter(this)
            presenter.listar(user!!.email)
        }
    }

    override fun setProgress(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletarContato() {

    }

    override fun listarContato(listContatos: List<Contato>) {
        rv!!.apply {
            layoutManager = LinearLayoutManager(ctx)
            adapter = RecyclerViewAdapter(listContatos, ctx)
            //adapter!!.notifyDataSetChanged()
        }
    }

    override fun error(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }
}