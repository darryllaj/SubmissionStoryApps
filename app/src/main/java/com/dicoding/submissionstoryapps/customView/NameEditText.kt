package com.dicoding.submissionstoryapps.customView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class NameEditText : AppCompatEditText {
    constructor(context: Context): super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }
    private fun init(){
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val name = s.toString()
                if (name.isEmpty() || name.isBlank()){
                    setError("Name Is Empty!!", null)
                } else if (name.length < 4) {
                    setError("Name tidak boleh kurang dari 4 karakter", null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
}