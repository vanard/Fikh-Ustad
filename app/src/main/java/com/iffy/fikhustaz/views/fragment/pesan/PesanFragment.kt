package com.iffy.fikhustaz.views.fragment.pesan


import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.ChatItem
import com.iffy.fikhustaz.data.model.Ustad
import com.iffy.fikhustaz.data.model.chat.Chat
import com.iffy.fikhustaz.util.FirebaseUtil
import com.iffy.fikhustaz.views.activity.chat.ChatActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_pesan.*
import kotlinx.coroutines.selects.select
import org.jetbrains.anko.support.v4.selector
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class PesanFragment : Fragment() , PesanContract.View{

    private lateinit var currentUser: Ustad
    private lateinit var presenter: PesanPresenter
    private val user = FirebaseAuth.getInstance().currentUser!!.uid

    private lateinit var dialog: ProgressDialog
    private val mAdapter = GroupAdapter<ViewHolder>()
    private var chatListener : ListenerRegistration? = null

    private var listItem = mutableListOf<ChatItem>()
    private var listChannel = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_messages.apply {
            layoutManager = LinearLayoutManager(this@PesanFragment.context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = mAdapter
        }

        presenter = PesanPresenter(this)

        FirebaseUtil.getCurrentUser {
            currentUser = it
            d("Pesan_Fragment", "$currentUser")
        }

        presenter.getLastMessageId()

    }

    override fun fillData(data: MutableList<String>) {
        d("FillData", "$data")
        mAdapter.clear()
        listChannel = data

        listChannel.forEach {
            chatListener = FirebaseUtil.getLastMessageListener(it, context!!, this::updateRecyclerViewMessages)
        }
    }

    private fun refreshRecyclerViewMessages(msg:List<ChatItem>) {
        d("FillData", "$msg")
        listItem.clear()
        listItem.addAll(msg)
        listItem.sortWith(
            compareBy<ChatItem> {it.message.type}.thenByDescending { it.message.time }
        )
        mAdapter.clear()
        mAdapter.apply {
            clear()
            addAll(listItem)
            setOnItemClickListener(onItemClick)
            notifyDataSetChanged()
        }
        mAdapter.notifyDataSetChanged()
    }

    private fun updateRecyclerViewMessages() {
        FirebaseUtil.getLastMessage(listChannel, context!!, this::refreshRecyclerViewMessages)
    }

    private val onItemClick = OnItemClickListener { it, view ->
        if (it is ChatItem) {
            if (it.message.senderId == user) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        if (chatListener != null){
            FirebaseUtil.removeListener(chatListener!!)
        }
    }

    override fun showLoad() {
        dialog = ProgressDialog.show(this@PesanFragment.context, "", "Please wait")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoad() {
        dialog.dismiss()
    }
    override fun showMsg(msg: String) {
        toast(msg)
    }

}
