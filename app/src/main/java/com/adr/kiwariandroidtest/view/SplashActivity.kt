package com.adr.kiwariandroidtest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.adr.kiwariandroidtest.R
import com.adr.kiwariandroidtest.presenter.SplashActivityPresenter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), ISplashActivityView {

    private val presenter by lazy { SplashActivityPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.onSplashStarted()
        iv_splash.startAnimation(AnimationUtils.loadAnimation(this, R.anim.splash_animation))
    }

    override fun onSplashFinished(isUserLoggedIn: Boolean) {
        if (isUserLoggedIn){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}