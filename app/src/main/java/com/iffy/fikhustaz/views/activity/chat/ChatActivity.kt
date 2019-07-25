package com.iffy.fikhustaz.views.activity.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.data.model.profile.Ustad
import com.iffy.fikhustaz.data.model.chat.TextMessage
import com.iffy.fikhustaz.util.FirebaseUtil
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.util.*

class ChatActivity : AppCompatActivity(), ChatContract.View {

    companion object {
        const val CHANNEL_ID = "chat"
        private const val CHANNEL_NAME= "Fikh Chat"
        private const val CHANNEL_DESC = "Fikh Chat"
    }

    private lateinit var currentUser: Ustad
    private lateinit var otherUserId: String

    private lateinit var messagesListenerRegistration: ListenerRegistration
    private var shouldInitRecyclerView = true
    private lateinit var messagesSection: Section
    private lateinit var presenter:ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra("USERNAME")

        presenter = ChatPresenter(this)
        FirebaseUtil.getCurrentUser {
            currentUser = it
        }
        otherUserId = intent.getStringExtra("USER_ID")
        presenter.getData(otherUserId)
    }

    override fun showData(channelId: String) {
        messagesListenerRegistration =
            FirebaseUtil.addChatMessagesListener(channelId, this, this::updateRecyclerView)

        send_btn_chat_log.setOnClickListener {
            if (et_chat_log.text.toString().isNotEmpty()){
                val messageToSend =
                    TextMessage(
                        et_chat_log.text.toString(), Calendar.getInstance().time,
                        FirebaseAuth.getInstance().currentUser!!.uid,
                        otherUserId, currentUser.nama!!, intent.getStringExtra("USERNAME")
                    )
                et_chat_log.text.clear()
                FirebaseUtil.sendMessage(messageToSend, channelId, currentUser.profilePicture!!)
            }
        }
    }

    private fun updateRecyclerView(messages: List<Item>) {
        fun init() {
            rv_chat_log.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                adapter = GroupAdapter<ViewHolder>().apply {
                    messagesSection = Section(messages)
                    this.add(messagesSection)
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = messagesSection.update(messages)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

        rv_chat_log.scrollToPosition(rv_chat_log.adapter!!.itemCount - 1)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                startActivity(intentFor<HomeActivity>("frg" to AppConst.CHAT_ACTIVITY).newTask().clearTask())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        startActivity(intentFor<HomeActivity>("frg" to AppConst.CHAT_ACTIVITY).newTask().clearTask())
    }
}
