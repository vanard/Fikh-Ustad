package com.iffy.fikhustaz.views.activity.detailmateri

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.IsiFikh
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_materi.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import android.os.Environment.getExternalStorageDirectory
import android.os.Environment
import android.os.StrictMode
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.Font


class DetailMateriActivity : AppCompatActivity() {

    private lateinit var a : IsiFikh
    private lateinit var uri : Uri

    private val FONT = "res/font/scheherazade_regular.ttf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_materi)

        if (intent != null) {
            a = intent.getParcelableExtra("data")

            supportActionBar?.title = a.title
            Picasso.get().load(a.image).placeholder(R.drawable.logo_app_fikh).into(img_detailmateri)
            tv_author_detailmateri.text = a.author
            tv_date_detailmateri.text = a.date
            tv_description_detailmateri.text = Html.fromHtml(a.description)

            val mFilePath = "${getExternalStorageDirectory()}/WebPageToPDF/${a.title}.pdf"

            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())

            val mDoc = Document()

            try{
                PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
                mDoc.open()
                mDoc.addAuthor(a.author)
                val bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED)
                val f = Font(bf)
                val p = Paragraph("${tv_description_detailmateri.text}", f)
                mDoc.add(p)
//                mDoc.add(Paragraph("${tv_description_detailmateri.text}"))
                mDoc.close()

                uri = Uri.fromFile(File(mFilePath))
            } catch (e : Exception) {
                toast(e.localizedMessage)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.materi_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> {
                startActivity(intentFor<HomeActivity>("frg" to AppConst.MATERI_ACTIVITY).newTask().clearTask())
                return true
            }
            R.id.menu_share -> {
                val i = Intent()
                i.action = Intent.ACTION_SEND
                i.type = "application/pdf"
                i.putExtra(Intent.EXTRA_STREAM, uri)

                startActivity(Intent.createChooser(i, "Pilih aplikasi share: "))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        startActivity(intentFor<HomeActivity>("frg" to AppConst.MATERI_ACTIVITY).newTask().clearTask())
    }
}
