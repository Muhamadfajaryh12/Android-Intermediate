package com.example.submissionintermediate.customView

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailCustom : AppCompatEditText{
    constructor(context: Context) : super(context){
        init()
    }
    constructor(context:Context,attrs: AttributeSet):super(context,attrs){
        init()
    }
    constructor(context:Context,attrs:AttributeSet,defStyleAttr:Int):super(context,attrs,defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)
        hint = "Email"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init(){
        addTextChangedListener(object:TextWatcher{

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int,after: Int) {
                if(!validateEmail(s.toString())){
                    setError("Masukkan Email dengan benar")
                }else{
                    error=null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun validateEmail(email:String):Boolean{
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email.toString())
        return matcher.matches()
    }
}