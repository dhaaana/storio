package com.dhana.storio.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dhana.storio.R

class PasswordEditText : AppCompatEditText {
    private var submitButton: View? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        hint = "Password"

        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        transformationMethod = PasswordTransformationMethod.getInstance()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 8) {
                    error = "Password must be 8 character"
                    submitButton?.isEnabled = false
                } else {
                    error = null
                    submitButton?.isEnabled = true
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    fun setSubmitButton(button: View) {
        submitButton = button
        submitButton?.isEnabled = text?.length!! >= 8
    }

}
