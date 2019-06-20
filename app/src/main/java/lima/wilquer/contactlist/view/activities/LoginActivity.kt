package lima.wilquer.contactlist.view.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.util.Constants
import lima.wilquer.contactlist.view.user.UserContract
import lima.wilquer.contactlist.view.user.UserPresenter

class LoginActivity : AppCompatActivity(), View.OnClickListener, UserContract.View {

    override lateinit var presenter: UserContract.Presenter
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener(this)
        button_cadastrar.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (!this::presenter.isInitialized) {
            UserPresenter(this)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_login -> {
                if (checkValues()) {
                    presenter.login(email!!, password!!)
                } else {
                    Toast.makeText(this, getString(R.string.erro_campos_incorretos), Toast.LENGTH_LONG).show()
                }
            }
            R.id.button_cadastrar -> {
                if (checkValues()) {
                    presenter.cadastrar(email!!, password!!)
                } else {
                    Toast.makeText(this, getString(R.string.erro_campos_incorretos), Toast.LENGTH_LONG).show()
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
                    button_login.text = getString(R.string.login)
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
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
        val it = Intent(this@LoginActivity, HomeActivity::class.java)
        it.putExtra(Constants.USER, user)
        startActivity(it)
        finish()
    }

    override fun buscarUser(user: User?) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
    }

    override fun cadastrarUser(user: User?) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
        val it = Intent(this@LoginActivity, HomeActivity::class.java)
        it.putExtra(Constants.USER, user)
        startActivity(it)
        finish()
    }

    override fun error(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
