package com.adr.kiwariandroidtest.presenter

import android.os.Handler
import android.util.Log
import com.adr.kiwariandroidtest.view.ISplashActivityView
import com.google.firebase.auth.FirebaseAuth


class SplashActivityPresenter(private val splashActivityView: ISplashActivityView) :
    ISplashActivityPresenter {

    override fun onSplashStarted() {
        Handler().postDelayed(Runnable {
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            val isUserLoggedIn = firebaseUser != null

            splashActivityView.onSplashFinished(isUserLoggedIn)
        }, 3000)
    }
}