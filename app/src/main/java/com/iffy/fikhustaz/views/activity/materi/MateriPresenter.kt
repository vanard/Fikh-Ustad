package com.iffy.fikhustaz.views.activity.materi

import android.content.Context
import android.widget.Toast
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

class MateriPresenter (v : MateriContract.View, ctx: Context) : MateriContract.Presenter {
    private val view = v
    private val mCtx = ctx
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun showFile(url: String) {
        view.showLoading()

        uiScope.launch {
            try {
                FileLoader.with(mCtx)
                    .load(url)
                    .fromDirectory("Materi Fikh", FileLoader.DIR_EXTERNAL_PUBLIC)
                    .asFile(object: FileRequestListener<File> {
                        override fun onError(p0: FileLoadRequest?, p1: Throwable?) {
                            Toast.makeText(mCtx, p1?.message, Toast.LENGTH_SHORT).show()
                            view.hideLoading()
                        }

                        override fun onLoad(p0: FileLoadRequest?, p1: FileResponse<File>?) {
                            val pdfFile = p1?.body

                            view.materiToUI(pdfFile)

                            view.hideLoading()
                        }
                    })
            } catch (e: HttpException) {
                view.showMsg(e.code().toString())
            } catch (e: Throwable) {
                view.showMsg("Ooops: Something else went wrong")
            }
        }
    }

}
