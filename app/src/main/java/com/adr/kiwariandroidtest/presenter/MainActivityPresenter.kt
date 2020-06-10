package com.adr.kiwariandroidtest.presenter

import com.adr.kiwariandroidtest.adapter.RVAdapterMain
import com.adr.kiwariandroidtest.model.UsersModel
import com.adr.kiwariandroidtest.view.IMainActivityView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivityPresenter(private val iMainActivityView: IMainActivityView): IMainActivityPresenter {

    override fun getAllContact() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser?.uid
        val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("users")

        firebaseDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val users: ArrayList<UsersModel.User> = ArrayList()

                for (dataSnapShot: DataSnapshot in p0.children){
                    val user = dataSnapShot.getValue(UsersModel.User::class.java)
                    if (user != null) {
                        users.add(user)
                    }
                }

                for (user: UsersModel.User in users){
                    if (user.uid == firebaseUser){
                        users.remove(user)
                        break
                    }
                }
                iMainActivityView.onSetData(users)
            }

        })
    }

    override fun userLogout() {
        val firebaseUser = FirebaseAuth.getInstance()
        firebaseUser.signOut()

        if (firebaseUser.currentUser == null){
            iMainActivityView.onLogoutStatus(true)
        } else {
            iMainActivityView.onLogoutStatus(false)
        }
    }
}