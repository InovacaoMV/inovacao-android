package lima.wilquer.contactlist.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.adicionar_editar_contato.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.Contato
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.util.Constants
import lima.wilquer.contactlist.util.Mask
import lima.wilquer.contactlist.util.Session
import lima.wilquer.contactlist.view.contatos.AdicionarEditarPresenter
import lima.wilquer.contactlist.view.contatos.ContatosContract

class AdicionarEditarContato : AppCompatActivity(), ContatosContract.ViewAE {

    override lateinit var presenter: ContatosContract.PresenterAE
    var flagEditar: Boolean = false
    var contato:Contato? = null
    val user : User = Session.loggedUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_editar_contato)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        contato = intent.getSerializableExtra(Constants.CONTATO) as Contato?

        if (contato != null){
            trocarTextos()
            flagEditar = true
        }

        edit_telefone.addTextChangedListener(Mask.mask(edit_telefone,Mask.FORMAT_FONE))

        add_editar_button.setOnClickListener {
            if (flagEditar) {
                presenter.editar(
                    Contato(
                        contato!!._id,
                        "",
                        edit_nome.text.toString(),
                        user.email,
                        edit_telefone.text.toString(),
                        ""
                    )
                )
            } else {
                presenter.cadastrar(
                    Contato(
                        "",
                        "",
                        edit_nome.text.toString(),
                        Session.loggedUser!!.email,
                        edit_telefone.text.toString(),
                        ""
                    )
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun trocarTextos(){
        add_editar_button.text = getString(R.string.editar)
        edit_nome.setText(contato!!.name)
        edit_telefone.setText(contato!!.cellphone)
    }

    override fun onResume() {
        super.onResume()
        if (!this::presenter.isInitialized) {
            AdicionarEditarPresenter(this)
        }
    }

    override fun setProgress(active: Boolean) {
        if(active) {
            progress_adicionar.visibility = View.VISIBLE
            add_editar_button.text = ""
        } else {
            progress_adicionar.visibility = View.INVISIBLE
            add_editar_button.text = if(flagEditar) getString(R.string.editar) else getString(R.string.cadastrar)
        }
        add_editar_button.isEnabled = !active
    }

    override fun retornoCadastrarEditar(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        finish()
    }

    override fun error(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}