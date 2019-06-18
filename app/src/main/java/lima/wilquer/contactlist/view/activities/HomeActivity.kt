package lima.wilquer.contactlist.view.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_home.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.User
import lima.wilquer.contactlist.view.contatos.ContatosFragment

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemReselectedListener {

    var user : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemReselectedListener(this)
        user = intent.getSerializableExtra("user") as User
    }

    override fun onNavigationItemReselected(p0: MenuItem) {
        when(p0.itemId){
            R.id.profile -> {
                /*supportFragmentManager.beginTransaction().apply {
                replace(R.id.framelayout, ProcurarFragment())
            }.commit()*/}
            R.id.phonelist -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.framelayout, ContatosFragment.newInstance(user!!))
                }.commit()
            }
        }
    }

    override fun onBackPressed() {
        if (bottom_navigation?.selectedItemId != R.id.phonelist) {
            bottom_navigation?.selectedItemId = R.id.phonelist
        } else {
            super.onBackPressed()
        }
    }
}