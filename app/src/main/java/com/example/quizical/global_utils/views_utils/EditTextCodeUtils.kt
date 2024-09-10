package com.example.quizical.global_utils.views_utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

/**
 * Executes code after the text in the EditText has changed.
 * @param code The code to be executed, taking the current text as a parameter.
 */
fun EditText.afterTextChange(code: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            code(text.toString())
        }
    })
}

/**
 * Executes code before the text in the EditText is changed.
 * @param code The code to be executed, taking the current text as a parameter.
 */
fun EditText.beforeTextChange(code: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            code(text.toString())
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {}
    })
}

/**
 * Executes code when the text in the EditText is changed.
 * @param code The code to be executed, taking the current text as a parameter.
 */
fun EditText.onTextChange(code: (text: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            code(text.toString())
        }

        override fun afterTextChanged(p0: Editable?) {}
    })
}

fun EditText.onEditorActionListener(onAction: (textView: TextView?) -> Boolean) {
    setOnEditorActionListener { textView, _, _ -> onAction(textView) }
}
