package lima.wilquer.contactlist.util

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_item.view.*
import lima.wilquer.contactlist.R
import lima.wilquer.contactlist.data.Contato
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import lima.wilquer.contactlist.view.activities.AdicionarEditarContato


class RecyclerViewAdapter(
    val listContatos: List<Contato>, val context: Context,
    val observerDeleteContato: ObserverDeleteContato
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    var lastPosition = listContatos.size - 1

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, p0, false))
    }

    override fun getItemCount(): Int {
        return listContatos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.nomeContato.text = listContatos[p1].name
        holder.telefoneContato.text = listContatos[p1].cellphone

        setAnimation(holder.itemView, p1)

        holder.editar_btn.setOnClickListener {
            val intent = Intent(context, AdicionarEditarContato::class.java)
            intent.putExtra("contato", listContatos[p1])
            context.startActivity(intent)
        }

        holder.deletar_btn.setOnClickListener {
            observerDeleteContato.deletarPosicao(p1)
        }
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            animation.duration = 1000
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.clearAnimation()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeContato = itemView.nome_contato!!
        val telefoneContato = itemView.telefone_contato!!
        val editar_btn = itemView.editar_btn!!
        val deletar_btn = itemView.delete_btn!!

        fun clearAnimation() {
            itemView.clearAnimation()
        }
    }

}