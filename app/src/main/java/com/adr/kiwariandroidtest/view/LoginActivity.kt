package com.adr.kiwariandroidtest.view

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adr.kiwariandroidtest.R
import com.adr.kiwariandroidtest.presenter.LoginActivityPresenter
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity(), ILoginActivityView {

    private val presenter by lazy { LoginActivityPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailRegex = "^(?=.*[a-z])(?=.*[@])(?=.*[.])(?=\\S+$).{6,}$"
        val passwordRegex = "^(?=.*[0-9])(?=\\S+$).{6,}$"
//        another regex option
//        val emailRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@])(?=\\S+$).{6,}$"
//        val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$"

        et_email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.isNotEmpty()) {
                    if (!Pattern.matches(emailRegex, s)){
                        et_email.error = "Please check your email again."
                    } else {
                        et_email.error = null
                    }
                }
            }

        })

        et_password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null && s.isNotEmpty()) {
                    if (!Pattern.matches(passwordRegex, s)){
                        et_password.error = "Please check your password again."
                    } else {
                        et_password.error = null
                    }
                }
            }

        })

        btn_login.setOnClickListener{
            val email = et_email.text.toString()
            val password = et_password.text.toString()

            if (email.isEmpty() || email.isBlank()){
                et_email.error = "This form must not be empty."
            } else if (password.isEmpty() || password.isBlank()){
                et_password.error = "This form must not be empty"
            } else {
                presenter.userLogin(email, password)
            }
        }
    }

    override fun onLoginSuccess() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onLoginFailed() {
        Toast.makeText(this, "Login failed. Please chek your email & password then try again.", Toast.LENGTH_SHORT).show()
    }
}