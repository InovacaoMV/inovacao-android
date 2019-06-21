package lima.wilquer.contactlist.view.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.util.Constants
import lima.wilquer.contactlist.util.Session
import lima.wilquer.contactlist.view.addEditar.AdicionarEditarActivity
import lima.wilquer.contactlist.view.listarDeletar.ListarDeletarFragment
import lima.wilquer.contactlist.view.perfil.PerfilFragment

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        user = intent.getSerializableExtra(Constants.USER) as User
        Session.loggedUser = user
        bottom_navigation.selectedItemId = R.id.phonelist
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        startActivity(Intent(this, AdicionarEditarActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.profile -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.framelayout, PerfilFragment.newInstance(user!!))
                }.commit()
            }
            R.id.phonelist -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.framelayout, ListarDeletarFragment.newInstance(user!!))
                }.commit()
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (bottom_navigation?.selectedItemId != R.id.phonelist) {
            bottom_navigation?.selectedItemId = R.id.phonelist
        } else {
            super.onBackPressed()
        }
    }
}