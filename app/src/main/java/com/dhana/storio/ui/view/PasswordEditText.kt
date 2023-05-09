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

        // Set the hint for the EditText
        hint = "Password"

        // Set the text alignment for the EditText
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        // Initialize the show/hide password button images
        showPasswordButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_home_black_24dp) as Drawable
        hidePasswordButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_notifications_black_24dp) as Drawable

        // Set the transformation method to PasswordTransformationMethod
        transformationMethod = PasswordTransformationMethod.getInstance()

        // Add text changed listener to the EditText to show/hide the password toggle button
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 8) {
                    error = "Password must be 8 character"
                } else {
                    error = null
                }

            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }

}
