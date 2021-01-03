package com.iffy.fikhustaz.views.activity.quran

import android.app.ProgressDialog
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.data.itemviews.AyatItem
import com.iffy.fikhustaz.data.local.db
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.data.model.quran.Surat
import com.iffy.fikhustaz.network.RetrofitFactory
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_surah.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast
import retrofit2.HttpException

class SurahActivity : AppCompatActivity(), SurahContract.View {

    val adapter = GroupAdapter<ViewHolder>()
    private var listAyat = mutableListOf<Surat>()
    private lateinit var quran: Quran

    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surah)

        val presenter = SurahPresenter(this, this)

        rv_ayat.adapter = adapter
        rv_ayat.layoutManager = LinearLayoutManager(this)
        rv_ayat.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        if (intent != null) {
            quran = intent.getParcelableExtra("quran")!!

                supportActionBar?.title = quran.nama

                presenter.initDataSql(quran.nomor)

        }else
            toast("Something went wrong.")

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                startActivity(intentFor<HomeActivity>("frg" to AppConst.QURAN_ACTIVITY).newTask().clearTask())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        startActivity(intentFor<HomeActivity>("frg" to AppConst.QURAN_ACTIVITY).newTask().clearTask())
    }

    override fun setDataSql(it: List<Surat>) {
        adapter.clear()
        listAyat.addAll(it)
        listAyat.forEach {
            adapter.add(AyatItem(it))
        }
    }

    override fun showLoad() {
        dialog = ProgressDialog.show(this@SurahActivity, "Preparing", "Loading")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoad() {
        dialog.dismiss()
    }

    override fun setData(it: List<Surat>) {
        adapter.clear()
        listAyat.addAll(it)
        listAyat.forEach {
            adapter.add(AyatItem(it))

            try{
                db.use{
                    insert(
                        Surat.TABLE_SURAH,
                        Surat.ID to quran.nomor,
                        Surat.SURAH_AR to it.ar,
                        Surat.SURAH_ID to it.id,
                        Surat.SURAH_NOMOR to it.nomor,
                        Surat.SURAH_TR to it.tr)
                }
            }catch (e: SQLiteConstraintException){
                Log.d("PTK", e.localizedMessage)
            }
        }
        hideLoad()
    }

    override fun showMsg(str: String) {
        toast(str)
    }
}
