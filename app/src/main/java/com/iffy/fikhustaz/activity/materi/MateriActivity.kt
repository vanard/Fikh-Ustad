package com.iffy.fikhustaz.activity.materi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.model.materi.Attachment
import kotlinx.android.synthetic.main.activity_materi.*
import org.jetbrains.anko.toast
import java.io.File

class MateriActivity : AppCompatActivity(),MateriContract.View {

    private lateinit var presenter : MateriPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi)

        presenter = MateriPresenter(this, this)

        if(intent != null){
            val data = intent.getParcelableArrayListExtra<Attachment>("materi")
            if (data != null){
                supportActionBar?.title = data[0].title
                presenter.showFile(data[0].url)
            }
        }
    }

    override fun materiToUI(pdfFile: File?) {
        pdf_view.fromFile(pdfFile)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, t -> Toast.makeText(this@MateriActivity, "Error while open page ${page}", Toast.LENGTH_SHORT).show() }
            .onRender { nbPages, pageWidth, pageHeight -> pdf_view.fitToWidth() }
            .enableAnnotationRendering(true)
            .invalidPageColor(Color.WHITE)
            .load()
    }

    override fun showMsg(msg: String) {
        toast(msg)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        pdf_view.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        pdf_view.visibility = View.VISIBLE
    }
}
