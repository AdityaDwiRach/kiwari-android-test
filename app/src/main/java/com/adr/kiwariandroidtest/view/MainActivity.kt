package com.adr.kiwariandroidtest.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adr.kiwariandroidtest.R
import com.adr.kiwariandroidtest.adapter.RVAdapterMain
import com.adr.kiwariandroidtest.model.UsersModel
import com.adr.kiwariandroidtest.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainActivityView {

    companion object {
        const val EXTRA_UID = "extra_uid"
    }

    private val presenter by lazy { MainActivityPresenter(this) }
    private val adapterRV by lazy { RVAdapterMain(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(tlbar_main)
        supportActionBar?.title = "Contact"

        rv_main.setHasFixedSize(true)
        rv_main.layoutManager = LinearLayoutManager(this)
        rv_main.adapter = adapterRV

        presenter.getAllContact()

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
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Logout failed. Please tak a moment then try again.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSetData(data: ArrayList<UsersModel.User>) {
        adapterRV.setListData(data)
        adapterRV.refreshData()
    }

    override fun onContactClicked(userId: String) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra(EXTRA_UID, userId)
        startActivity(intent)
    }
}