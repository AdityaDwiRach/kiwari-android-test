package com.adr.kiwariandroidtest.presenter

import com.adr.kiwariandroidtest.view.ILoginActivityView
import com.google.firebase.auth.FirebaseAuth

class LoginActivityPresenter(private val iLoginActivityView: ILoginActivityView): ILoginActivityPresenter {
    override fun userLogin(email: String, password: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                iLoginActivityView.onLoginSuccess()
            } else {
                iLoginActivityView.onLoginFailed()
            }
        }
    }
}