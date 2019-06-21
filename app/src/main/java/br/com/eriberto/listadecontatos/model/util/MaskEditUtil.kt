package br.com.eriberto.listadecontatos.model.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.*

object MaskEditUtil {

    /*EXEMPLO DE UTILIZAÇÃO EM JAVA
        editTextCpf.addTextChangedListener(MaskEditUtil.mask(editTextCpf, MaskEditUtil.FORMAT_CPF));
     */

    const val FORMAT_CPF = "###.###.###-##"
    const val FORMAT_CNPJ = "##.###.###/####-##"
    const val FORMAT_CELULAR = "(##)#####-####"
    const val FORMAT_TELEFONE = "####-####"
    const val FORMAT_CEP = "#####-###"
    const val FORMAT_DATE = "##/##/####"
    const val FORMAT_HOUR = "##:##"

    fun mask(ediTxt: EditText, mask: String): TextWatcher {
        return object : TextWatcher {
            var isUpdating: Boolean = false
            var old = ""

            override
            fun afterTextChanged(s: Editable) {
            }

            override
            fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override
            fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && str.length > old.length) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        break
                    }

                    i++
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }
        }
    }

    private fun unmask(s: String): String {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "").replace("[/]".toRegex(), "").replace("[(]".toRegex(), "").replace("[ ]".toRegex(), "").replace("[:]".toRegex(), "").replace("[)]".toRegex(), "")
    }

    fun addMaskString(textoAFormatar: String, mask: String): String {
        var formatado = ""
        var i = 0
        // vamos iterar a mascara, para descobrir quais caracteres vamos adicionar e quando...
        for (m in mask.toCharArray()) {
            if (m != '#') { // se não for um #, vamos colocar o caracter informado na máscara
                formatado += m
                continue
            }
            // Senão colocamos o valor que será formatado
            try {
                formatado += textoAFormatar[i]
            } catch (e: Exception) {
                break
            }

            i++
        }
        return formatado
    }

    fun converterValorMonetario(valor: Float): String {
        return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(valor.toDouble())
    }
}