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

class PasswordEditText : AppCompatEditText, OnTouchListener {

    private lateinit var showPasswordButtonImage: Drawable
    private lateinit var hidePasswordButtonImage: Drawable
    private var isPasswordVisible = false

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
        hint = "Enter your password"

        // Set the text alignment for the EditText
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        // Initialize the show/hide password button images
        showPasswordButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_home_black_24dp) as Drawable
        hidePasswordButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_notifications_black_24dp) as Drawable

        // Add touch listener to the EditText
        setOnTouchListener(this)

        // Add text changed listener to the EditText to show/hide the password toggle button
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showPasswordToggle() else hidePasswordToggle()
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

    private fun showPasswordToggle() {
        setButtonDrawables(endOfTheText = if (isPasswordVisible) hidePasswordButtonImage else showPasswordButtonImage)
    }

    private fun hidePasswordToggle() {
        setButtonDrawables()
    }

    // Set the button images to appear to the right of the text
    private fun setButtonDrawables(startOfTheText: Drawable? = null, topOfTheText:Drawable? = null, endOfTheText:Drawable? = null, bottomOfTheText: Drawable? = null){
        setCompoundDrawablesWithIntrinsicBounds(startOfTheText, topOfTheText, endOfTheText, bottomOfTheText)
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            val passwordToggleStart = width - paddingRight - compoundDrawables[2].intrinsicWidth
            if (event.x > passwordToggleStart) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        togglePasswordVisibility()
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        transformationMethod = if (isPasswordVisible) null else PasswordTransformationMethod.getInstance()
        setButtonDrawables(endOfTheText = if (isPasswordVisible) hidePasswordButtonImage else showPasswordButtonImage)
    }
}
