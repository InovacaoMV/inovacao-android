package br.com.eriberto.listadecontatos.model.util

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtil {
    fun isConnectedNetwork(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}