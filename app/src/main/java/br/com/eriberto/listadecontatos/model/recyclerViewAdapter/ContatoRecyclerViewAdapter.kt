package br.com.eriberto.listadecontatos.model.recyclerViewAdapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.eriberto.listadecontatos.R
import br.com.eriberto.listadecontatos.model.interfaces.InteracaoListaContatos
import br.com.eriberto.listadecontatos.model.modelo.Contato
import kotlinx.android.synthetic.main.item_contato.view.*

class ContatoRecyclerViewAdapter(
    private val mValues: ArrayList<Contato>,
    private val interacaoListaContatos: InteracaoListaContatos
) : RecyclerView.Adapter<ContatoRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val contato = v.tag as Contato
            interacaoListaContatos.selecionouContato(contato = contato)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contato, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contato = mValues[position]

        holder.tvNome.text = contato.name
        if (contato.person_email.isNullOrEmpty()) {
            holder.tvEmail.visibility = View.GONE
            holder.tvDescricaoEmail.visibility = View.GONE
        } else {
            holder.tvEmail.visibility = View.VISIBLE
            holder.tvDescricaoEmail.visibility = View.VISIBLE
            holder.tvEmail.text = contato.person_email
        }
        holder.tvTelefone.text = contato.cellphone

        with(holder.mView) {
            tag = contato
            setOnClickListener(mOnClickListener)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val tvNome: TextView = mView.tvNome
        val tvEmail: TextView = mView.tvEmail
        val tvDescricaoEmail: TextView = mView.tvDescricaoEmail
        val tvTelefone: TextView = mView.tvTelefone
    }
}