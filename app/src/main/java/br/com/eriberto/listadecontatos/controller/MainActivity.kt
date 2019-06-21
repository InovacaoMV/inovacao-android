package br.com.eriberto.listadecontatos.controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.eriberto.listadecontatos.R
import br.com.eriberto.listadecontatos.model.enums.TIPO_ACESSO
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, LoginCadastroActivity::class.java)

        btTenhoConta.setOnClickListener {
            intent.putExtra("tipoAcesso",TIPO_ACESSO.ENTRAR)
            startActivity(intent)
            finish()
        }

        btCadastroConta.setOnClickListener {
            intent.putExtra("tipoAcesso",TIPO_ACESSO.CADASTRAR)
            startActivity(intent)
            finish()
        }
    }
}
