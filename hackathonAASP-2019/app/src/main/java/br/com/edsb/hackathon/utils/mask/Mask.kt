package br.com.edsb.hackathon.utils.mask

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class Mask {
    companion object {
        fun unmask(s: String?): String {
            return s?.replace("[^0-9]*".toRegex(), "")!!
        }

        fun insert(mask: String, editText: EditText): TextWatcher {
            return object : TextWatcher {
                var isUpdating: Boolean = false
                var old = ""

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    val str = Mask.unmask(s.toString())

                    var mascara = ""
                    if (isUpdating) {
                        old = str
                        isUpdating = false
                        return
                    }
                    var i = 0
                    for (m in mask.toCharArray()) {
                        if (m != '#' && str.length > old.length || m != '#'
                                && str.length < old.length && str.length != i) {
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
                    editText.setText(mascara)
                    editText.setSelection(mascara.length)
                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                               after: Int) {
                }

                override fun afterTextChanged(s: Editable) {}
            }
        }

        fun insert(mask: String, text: String?): String {
            val str = Mask.unmask(text!!)
            var mascara = ""

            var i = 0
            for (m in mask) {
                if (m != '#') {
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

            return mascara
        }
    }
}