package com.dhana.storio.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dhana.storio.R

class PasswordEditText : AppCompatEditText {

    private lateinit var showPasswordButtonImage: Drawable
    private lateinit var hidePasswordButtonImage: Drawable
    private var isPasswordVisible = true

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        hint = "Password"

        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        showPasswordButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_home_black_24dp) as Drawable
        hidePasswordButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_notifications_black_24dp) as Drawable

        transformationMethod = PasswordTransformationMethod.getInstance()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error = if (s.length < 8) {
                    "Password must be 8 character"
                } else {
                    null
                }

            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

}
