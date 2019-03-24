package com.iffy.fikhustaz.views.fragment.pesan

import android.util.Log
import com.iffy.fikhustaz.util.FirebaseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PesanPresenter (v: PesanContract.View) : PesanContract.Presenter {
    private var view = v
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var currentChannelId: MutableList<String> = mutableListOf()

    override fun getLastMessageId() {
        view.showLoad()
        uiScope.async {
            try {
                FirebaseUtil.getChatChannel {
                    currentChannelId.addAll(it)
                    Log.d("Pesan_Fragment", "$currentChannelId")
                    view.fillData(currentChannelId)
                    view.hideLoad()
                }
            }catch (e: Throwable) {
                view.showMsg("Ooops: Something else went wrong")
            }
        }
    }

}