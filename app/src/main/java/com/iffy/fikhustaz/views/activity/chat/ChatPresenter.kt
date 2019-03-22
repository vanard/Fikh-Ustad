package com.iffy.fikhustaz.views.activity.chat

import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatPresenter(v: ChatContract.View): ChatContract.Presenter{
    private val view = v
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun getData(id: String) {
        uiScope.launch {
            FirebaseUtil.getOrCreateChatChannel(id) { channelId ->
                view.showData(channelId)
            }
        }
    }

}