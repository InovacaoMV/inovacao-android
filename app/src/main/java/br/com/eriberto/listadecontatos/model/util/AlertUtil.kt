package br.com.eriberto.listadecontatos.model.util

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.app.AlertDialog
import android.widget.Toast
import br.com.eriberto.listadecontatos.R

object AlertUtil {

    fun showToastShort(context: Context, texto: String) {
        Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
    }

    fun showToastLong(context: Context, texto: String) {
        Toast.makeText(context, texto, Toast.LENGTH_LONG).show()
    }

    fun showDialogGenerico(context: Context, titulo: String?, mensagem: String?, icon: Drawable?, fecharTela: Boolean) {
        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setMessage(mensagem)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
                if (fecharTela) (context as Activity).finish()
            }
            .setIcon(icon)
            .create()
            .show()
    }

    fun showDialogSemInternet(context: Context, fecharTela: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.titulo_sem_conexao))
                .setMessage(context.getString(R.string.titulo_sem_conexao))
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, which ->
                    dialog.cancel()
                    if (fecharTela) (context as Activity).finish()
                }
                //.setIcon(context.getDrawable(R.drawable.ic_network_off))
                .create()
                .show()
        else {
            AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.titulo_sem_conexao))
                .setMessage(context.getString(R.string.titulo_sem_conexao))
                .setCancelable(false)
                .setPositiveButton("OK") { dialog, which ->
                    dialog.cancel()
                    if (fecharTela) (context as Activity).finish()
                }
                .create()
                .show()
        }
    }
}