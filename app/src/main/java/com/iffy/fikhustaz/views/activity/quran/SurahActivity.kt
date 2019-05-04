package com.iffy.fikhustaz.views.activity.quran

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.AppConst
import com.iffy.fikhustaz.data.itemviews.AyatItem
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
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import retrofit2.HttpException

class SurahActivity : AppCompatActivity(), SurahContract.View {

    val adapter = GroupAdapter<ViewHolder>()
    private var listAyat = mutableListOf<Surat>()

    private lateinit var dialog: ProgressDialog
    val presenter = SurahPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surah)

        rv_ayat.adapter = adapter
        rv_ayat.layoutManager = LinearLayoutManager(this)
        rv_ayat.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        if (intent != null) {
            val title = intent.getParcelableExtra<Quran>("quran")
            if (title != null) {
                supportActionBar?.title = title.nama

                presenter.initData(title.nomor)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
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

    override fun showLoad() {
        dialog = ProgressDialog.show(this@SurahActivity, "", "Loading")
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
        }
    }
}
