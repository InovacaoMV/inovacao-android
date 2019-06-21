package lima.wilquer.contactlist.view.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.util.Constants
import lima.wilquer.contactlist.view.activities.HomeActivity
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.longToast

class LoginActivity : AppCompatActivity(), View.OnClickListener, LoginContract.View {

    override lateinit var presenter: LoginContract.Presenter
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.setTitle(R.string.login_txt)
        button_login.setOnClickListener(this)
        button_cadastrar.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (!this::presenter.isInitialized) {
            LoginPresenter(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_login -> {
                if (checkValues()) {
                    presenter.login(email!!, password!!)
                } else {
                    longToast(getString(R.string.erro_campos_incorretos))
                }
            }
            R.id.button_cadastrar -> {
                if (checkValues()) {
                    presenter.cadastrar(email!!, password!!)
                } else {
                    longToast(getString(R.string.erro_campos_incorretos))
                }
            }
        }
    }

    fun checkValues(): Boolean {
        if (edit_login.text.toString().isEmpty()) return false
        if (edit_password.text.toString().isEmpty()) return false

        email = edit_login.text.toString()
        password = edit_password.text.toString()
        return true
    }

    override fun setProgress(active: Boolean, flagButton: Int) {
        button_login.isEnabled = !active
        button_cadastrar.isEnabled = !active

        when (flagButton) {
            1 -> {
                if (active) {
                    progress_login.visibility = View.VISIBLE
                    button_login.text = ""
                } else {
                    progress_login.visibility = View.INVISIBLE
                    button_login.text = getString(R.string.login_txt)
                }
            }
            2 -> {
                if (active) {
                    progress_cadastrar.visibility = View.VISIBLE
                    button_cadastrar.text = ""
                } else {
                    progress_cadastrar.visibility = View.INVISIBLE
                    button_cadastrar.text = getString(R.string.cadastrar)
                }
            }
        }
    }

    override fun loginUser(user: User?) {
        longToast(getString(R.string.login_sucesso))
        val it = Intent(this@LoginActivity, HomeActivity::class.java)
        it.putExtra(Constants.USER, user)
        startActivity(it)
        finish()
    }

    override fun buscarUser(user: User?) {
        longToast(user.toString())
    }

    override fun cadastrarUser(user: User?) {
        longToast(getString(R.string.cadastro_sucesso))
        val it = Intent(this@LoginActivity, HomeActivity::class.java)
        it.putExtra(Constants.USER, user)
        startActivity(it)
        finish()
    }

    override fun error(msg: String) {
        longToast(msg)
    }
}
