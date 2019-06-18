package lima.wilquer.contactlist.util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_item.view.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.Contato

class RecyclerViewAdapter(val listContatos : List<Contato>, val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, p0, false))
    }

    override fun getItemCount(): Int {
        return listContatos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.nomeContato.text = listContatos[p1].name
        holder.telefoneContato.text = listContatos[p1].cellphone
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeContato = itemView.nome_contato
        val telefoneContato = itemView.telefone_contato
    }

}