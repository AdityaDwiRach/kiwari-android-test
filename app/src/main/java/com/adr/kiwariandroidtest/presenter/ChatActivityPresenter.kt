package com.adr.kiwariandroidtest.presenter

import android.util.Log
import com.adr.kiwariandroidtest.model.TextMessageModel
import com.adr.kiwariandroidtest.model.UsersModel
import com.adr.kiwariandroidtest.view.IChatActivityView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class ChatActivityPresenter(private val iChatActivityView: IChatActivityView) :
    IChatActivityPresenter {
    companion object {
        const val COLLECTION_KEY = "chat"
    }

    private val fireStoreChat by lazy {
        FirebaseFirestore.getInstance().collection(COLLECTION_KEY)
    }

    override fun findAvatarName(uid: String) {
        val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("users")

        firebaseDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var avatar = ""
                var name = ""

                for (dataSnapShot: DataSnapshot in p0.children) {
                    val user = dataSnapShot.getValue(UsersModel.User::class.java)
                    if (user != null && user.uid == uid) {
                        avatar = user.avatar
                        name = user.name
                        break
                    }
                }

                iChatActivityView.setAvatarName(avatar, name)
            }

        })
    }

    override fun sendMessage(textMessage: TextMessageModel.TextMessage) {
//        val firestoreChat = FirebaseFirestore.getInstance().collection(COLLECTION_KEY).document(
//            DOCUMENT_KEY
//        )
        fireStoreChat.add(textMessage)
//        set(textMessage)
            .addOnSuccessListener {
                iChatActivityView.onSuccessSendMessage()
                updateChat()
            }
            .addOnFailureListener {
                iChatActivityView.onFailureSendMessage()
            }
    }

    override fun updateChat() {
//        fireStoreChat.orderBy("time", Query.Direction.ASCENDING)
        fireStoreChat.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            when {
                firebaseFirestoreException != null -> iChatActivityView.onChatUpdatedFailure()
                documentSnapshot != null -> {
                    val data: ArrayList<TextMessageModel.TextMessage> = ArrayList()
                    for (x in 0 until documentSnapshot?.documents!!.size) {
                        val everyData = documentSnapshot?.documents?.get(x)?.data
                        val senderId = everyData?.get("senderId").toString()
                        val receiverId = everyData?.get("receiverId").toString()
                        val text = everyData?.get("text").toString()
                        val time = everyData?.get("time").toString().toLong()

                        val newTextMessage =
                            TextMessageModel.TextMessage(text, senderId, receiverId, time)
                        data.add(newTextMessage)
                    }
                    data.sortWith(Comparator { o1, o2 -> o1?.time?.compareTo(o2?.time!!)!! })
                    iChatActivityView.onChatUpdatedSuccess(data)
                }
            }


//            Log.d("chat", documentSnapshot?.documents?.get(0)?.data.toString())
//            Log.d("chat", data.toString())
        }
    }


}