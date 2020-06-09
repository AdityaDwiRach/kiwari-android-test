package com.adr.kiwariandroidtest.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.adr.kiwariandroidtest.R
import com.adr.kiwariandroidtest.presenter.IMainActivityPresenter
import com.adr.kiwariandroidtest.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainActivityView {
    private val presenter by lazy { MainActivityPresenter(this, this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(tlbar_main)
        supportActionBar?.title = "testiiiiiing"

        presenter.getCurrentUid()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.logout -> presenter.userLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun chatPartner(avatar: String, name: String) {

    }

    override fun onLogoutStatus(status: Boolean) {
        if (status){
            finish()
        } else {
            Toast.makeText(this, "Logout failed. Please tak a moment then try again.", Toast.LENGTH_SHORT).show()
        }
    }

}