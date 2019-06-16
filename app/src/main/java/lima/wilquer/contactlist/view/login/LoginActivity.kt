package lima.wilquer.contactlist.view.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.view.user.UserContract
import lima.wilquer.contactlist.view.user.UserPresenter

class LoginActivity : AppCompatActivity(), View.OnClickListener, UserContract.View {

    override lateinit var presenter: UserContract.Presenter
    private var email: String? = null
    private var password: String? = null
    private lateinit var id: String

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
                    Toast.makeText(this, "Verifique se os campos então corretos.", Toast.LENGTH_LONG).show()
                }
            }
            R.id.button_cadastrar -> {
                if (checkValues()) {
                    //presenter.atualizar(id, email!!, password!!)
                    presenter.delete(id)
                    //presenter.cadastrar(email!!, password!!)
                } else {
                    Toast.makeText(this, "Verifique se os campos então corretos.", Toast.LENGTH_LONG).show()
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

    override fun setProgress(active: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loginUser(user: User?) {
        id = user!!._id
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
    }

    override fun buscarUser(user: User?) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
    }

    override fun cadastrarUser(user: User?) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
    }

    override fun deleteUser(_id: String) {
        Toast.makeText(this, _id, Toast.LENGTH_LONG).show()
    }

    override fun atualizarUser(user: User?) {
        Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show()
    }

    override fun error(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
