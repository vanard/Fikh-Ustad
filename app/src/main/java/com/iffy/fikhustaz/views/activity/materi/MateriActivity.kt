package com.iffy.fikhustaz.views.activity.materi

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.data.model.materi.islamhouse.Attachment
import com.iffy.fikhustaz.views.activity.HomeActivity
import kotlinx.android.synthetic.main.activity_materi.*
import org.jetbrains.anko.*
import java.io.File
import android.os.StrictMode
import com.iffy.fikhustaz.data.model.materi.MateriUstad


class MateriActivity : AppCompatActivity(),MateriContract.View {

    private lateinit var presenter : MateriPresenter
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi)

        presenter = MateriPresenter(this, this)

        if(intent != null){
            val data = intent.getParcelableExtra<MateriUstad>("materi")
            if (data != null){
                supportActionBar?.title = data.title
                presenter.showFile(data.file)

                val builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
            }
        }
    }

    override fun materiToUI(pdfFile: File?) {
        uri = Uri.fromFile(pdfFile)

        pdf_view.fromFile(pdfFile)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, t -> Toast.makeText(this@MateriActivity, "Error while open page $page", Toast.LENGTH_SHORT).show() }
            .onRender { nbPages, pageWidth, pageHeight -> pdf_view.fitToWidth() }
            .enableAnnotationRendering(true)
            .invalidPageColor(Color.WHITE)
            .load()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.materi_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                startActivity(intentFor<HomeActivity>("frg" to AppConst.MATERI_ACTIVITY).newTask().clearTask())
            }
            R.id.menu_share -> {
                if (uri != null) {
                    val i = Intent()
                    i.action = Intent.ACTION_SEND
                    i.type = "application/pdf"
                    i.putExtra(Intent.EXTRA_STREAM, uri)

                    this@MateriActivity.startActivity(i)
                }
                else
                    toast("PDF share is not ready.")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        startActivity(intentFor<HomeActivity>("frg" to AppConst.MATERI_ACTIVITY).newTask().clearTask())
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
