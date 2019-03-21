package com.iffy.fikhustaz.fragment.pesan


import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ListenerRegistration
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.activity.chat.ChatActivity
import com.iffy.fikhustaz.data.itemviews.ChatItem
import com.iffy.fikhustaz.data.model.Ustad
import com.iffy.fikhustaz.util.FirebaseUtil
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_pesan.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class PesanFragment : Fragment() , PesanContract.View{

    private lateinit var currentUser: Ustad
    private lateinit var messagesListenerRegistration: ListenerRegistration
    private lateinit var messagesSection: Section
    private lateinit var presenter: PesanPresenter
    val mAdapter = GroupAdapter<ViewHolder>()

    private var shouldInitRecyclerView = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter = PesanPresenter(this)

        FirebaseUtil.getCurrentUser {
            currentUser = it
            d("Pesan_Fragment", "$currentUser")
        }

        presenter.getLastMessage()

    }

    override fun fillData(data: MutableList<String>) {
        for (doc in data){
            messagesListenerRegistration =
                FirebaseUtil.addChatListener(doc, context!!, this::updateRecyclerView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        FirebaseUtil.removeListener(messagesListenerRegistration)
        shouldInitRecyclerView = true
    }

    private fun updateRecyclerView(messages: List<Item>) {
        fun init() {
            rv_messages.apply {
                layoutManager = LinearLayoutManager(this@PesanFragment.context)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                adapter = mAdapter.apply {
                    messages.forEach {
                        messagesSection = Section(it)
                        add(messagesSection)
                        setOnItemClickListener(onItemClick)
                    }
                }
            }
            shouldInitRecyclerView = false
        }

        fun updateItems() = messagesSection.update(messages)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()


    }

    private val onItemClick = OnItemClickListener { it, view ->
        if (it is ChatItem) {
            if (it.message.senderName == currentUser.nama) {
                startActivity<ChatActivity>(
                    "USER_ID" to it.message.recipientId,
                    "USERNAME" to it.message.recipientName
                )
            }else{
                startActivity<ChatActivity>(
                    "USER_ID" to it.message.senderId,
                    "USERNAME" to it.message.senderName
                )
            }
        }
    }

    override fun showLoad() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        progressBar.visibility = View.GONE
    }
    override fun showMsg(msg: String) {
        toast(msg)
    }

}
