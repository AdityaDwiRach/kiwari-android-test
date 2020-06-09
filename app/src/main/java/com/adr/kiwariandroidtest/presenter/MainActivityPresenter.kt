package com.adr.kiwariandroidtest.presenter

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.adr.kiwariandroidtest.model.UsersModel
import com.adr.kiwariandroidtest.view.IMainActivityView
import com.adr.kiwariandroidtest.view.SplashActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MainActivityPresenter(private val iMainActivityView: IMainActivityView, private val context: Context): IMainActivityPresenter {
    override fun getCurrentUid() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser?.uid
        val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("users")

        firebaseDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val test: ArrayList<UsersModel.User> = p0.value as ArrayList<UsersModel.User>

//                for (user: UsersModel.User in test){
//                    if (user.uid == firebaseUser){
//
//                    }
//                }
//                Log.d("getdatabasevalue", p0.getValue(UsersModel.Users::class.java).toString())
                Log.d("getdatabasevalue",test.toString())
            }

        })
    }

    override fun userLogout() {
        val firebaseUser = FirebaseAuth.getInstance()
        firebaseUser.signOut()

        if (firebaseUser.currentUser == null){
            iMainActivityView.onLogoutStatus(true)
            context.startActivity(Intent(context, SplashActivity::class.java))
        } else {
            iMainActivityView.onLogoutStatus(false)
        }
    }
}