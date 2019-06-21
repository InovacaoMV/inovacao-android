package lima.wilquer.contactlist.view.perfil

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_perfil.*
import kotlinx.android.synthetic.main.fragment_perfil.view.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.util.Constants
import lima.wilquer.contactlist.util.Session
import lima.wilquer.contactlist.view.login.LoginActivity
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.withArguments

class PerfilFragment : Fragment(), PerfilContract.View, View.OnClickListener {

    companion object {
        fun newInstance(user: User) = PerfilFragment().withArguments(Pair(Constants.USER, user))
    }

    override lateinit var presenter: PerfilContract.Presenter
    var user: User? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_perfil, container, false)
        user = arguments!![Constants.USER] as User

        view.button_atualizar.setOnClickListener(this)
        view.button_deletar.setOnClickListener(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        edit_email.setText(user!!.email)
        if (!this::presenter.isInitialized) {
            PerfilPresenter(this)
        }
    }

    fun checkValues(): Boolean {
        if (edit_email.text.toString().isEmpty()) return false
        if (edit_senha.text.toString().isEmpty()) return false

        email = edit_email.text.toString()
        password = edit_senha.text.toString()
        return true
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_atualizar -> {
                if (checkValues()) {
                    presenter.atualizar(user!!._id, email!!, password!!)
                } else {
                    longToast(getString(R.string.erro_campos_incorretos))
                }
            }
            R.id.button_deletar -> {
                presenter.delete(user!!._id)

            }
        }
    }

    override fun setProgress(active: Boolean, flagButton: Int) {
        button_deletar.isEnabled = !active
        button_atualizar.isEnabled = !active

        when(flagButton){
            1 -> {
                if(active){
                    progress_deletar.visibility = View.VISIBLE
                    button_deletar.text = ""
                }else {
                    progress_deletar.visibility = View.INVISIBLE
                    button_deletar.text = getString(R.string.deletar_usuario)
                }
            }
            2 -> {
                if(active){
                    progress_atualizar.visibility = View.VISIBLE
                    button_atualizar.text = ""
                }else {
                    progress_atualizar.visibility = View.INVISIBLE
                    button_atualizar.text = getString(R.string.atualizar_usuario)
                }
            }
        }
    }

    override fun deleteUser(_id: String) {
        longToast(getString(R.string.delete_user))
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    override fun atualizarUser(user: User?) {
        Session.loggedUser = user
        longToast(getString(R.string.atualizar_user))
    }

    override fun error(msg: String) {
        longToast(msg)
    }
}