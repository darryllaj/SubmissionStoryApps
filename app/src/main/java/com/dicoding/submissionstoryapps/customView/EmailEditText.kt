package com.dicoding.submissionstoryapps.customView

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EmailEditText: AppCompatEditText{

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

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val email = text.toString()
                if (email.isEmpty() || email.isBlank()) {
                    error = "Please Input Your Email!!"
                } else if (!email.emailValidate()) {
                    error = "Email Is Not Valid!!"
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
            fun String.emailValidate(): Boolean{
                return !TextUtils.isEmpty(this.toString()) && Patterns.EMAIL_ADDRESS.matcher(this.toString())
                    .matches()
            }
        })
    }
}
