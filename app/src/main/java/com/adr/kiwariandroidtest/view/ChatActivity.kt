package com.adr.kiwariandroidtest.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adr.kiwariandroidtest.R
import com.adr.kiwariandroidtest.adapter.RVAdapterChat
import com.adr.kiwariandroidtest.model.TextMessageModel
import com.adr.kiwariandroidtest.presenter.ChatActivityPresenter
import com.adr.kiwariandroidtest.view.MainActivity.Companion.EXTRA_UID
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.custom_action_bar.*
import java.util.*

class ChatActivity : AppCompatActivity(), IChatActivityView {

    private val chatActivityPresenter by lazy { ChatActivityPresenter(this) }
    private val rvAdapterChat by lazy { RVAdapterChat() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val firebaseUser = FirebaseAuth.getInstance().currentUser?.uid
        var anotherFirebaseUser = ""
        val firestoreChat = FirebaseFirestore.getInstance().collection(ChatActivityPresenter.COLLECTION_KEY)

        setSupportActionBar(tlbar_chat)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.custom_action_bar)

        rv_chat.setHasFixedSize(true)
        rv_chat.layoutManager = LinearLayoutManager(this)
        rv_chat.adapter = rvAdapterChat

        if (firebaseUser != null) {
            rvAdapterChat.setCurrentFirebaseUser(firebaseUser)
        }

        if (intent.getStringExtra(EXTRA_UID) != null){
            anotherFirebaseUser = intent.getStringExtra(EXTRA_UID)!!
            chatActivityPresenter.findAvatarName(intent.getStringExtra(EXTRA_UID)!!)
        }

        fab_chat.setOnClickListener {
            val message = et_chat.text.toString()
            val time = Calendar.getInstance().timeInMillis

            if (message.isNotEmpty() && firebaseUser != null) {

                val textMessage = TextMessageModel.TextMessage(message, firebaseUser, anotherFirebaseUser, time)
                chatActivityPresenter.sendMessage(textMessage)
            }
        }

        firestoreChat.addSnapshotListener { _, _ ->
            chatActivityPresenter.updateChat()
        }
    }

    override fun setAvatarName(avatar: String, name: String) {
        Glide.with(this).load(avatar).into(civ_chat)
        tv_chat.text = name
    }

    override fun onSuccessSendMessage() {
        et_chat.setText("")
        rv_chat.scrollToPosition(rvAdapterChat.itemCount -1)
    }

    override fun onFailureSendMessage() {
        Toast.makeText(this, "Send message failure. Please try again.", Toast.LENGTH_SHORT).show()
    }

    override fun onChatUpdatedSuccess(listData: ArrayList<TextMessageModel.TextMessage>) {
        rvAdapterChat.setListData(listData)
        rvAdapterChat.refreshData()
        rv_chat.scrollToPosition(rvAdapterChat.itemCount -1)
    }

    override fun onChatUpdatedFailure() {
        Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show()
    }
}