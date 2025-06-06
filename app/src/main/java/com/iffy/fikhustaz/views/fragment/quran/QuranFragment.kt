package com.iffy.fikhustaz.views.fragment.quran


import android.app.ProgressDialog
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.QuranItem
import com.iffy.fikhustaz.data.local.db
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_quran.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.support.v4.toast

class QuranFragment : Fragment(), QuranContract.View {

    val adapter = GroupAdapter<ViewHolder>()
    private var listQuran = mutableListOf<Quran>()
    private var listDisplay = mutableListOf<Quran>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val presenter = QuranPresenter(this, context!!)

        (activity as HomeActivity).supportActionBar?.title = "Al - Qur'an"
        setHasOptionsMenu(true)

        rv_quran.adapter = adapter
        rv_quran.layoutManager = LinearLayoutManager(context)
        rv_quran.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        presenter.initDataSQL()
    }

    override fun setData(it: List<Quran>) {
        adapter.clear()
        listQuran.addAll(it)
        listDisplay.addAll(it)
        listDisplay.forEach {
            adapter.add(QuranItem(it))

            try{
                context?.db?.use{
                    insert(
                        Quran.TABLE_QURAN,
                        Quran.QURAN_ARTI to it.arti,
                        Quran.QURAN_ASMA to it.asma,
                        Quran.QURAN_AUDIO to it.audio,
                        Quran.QURAN_AYAT to it.ayat,
                        Quran.QURAN_KET to it.keterangan,
                        Quran.QURAN_NAMA to it.nama,
                        Quran.QURAN_NOMOR to it.nomor,
                        Quran.QURAN_RUKUK to it.rukuk,
                        Quran.QURAN_TIPE to it.type,
                        Quran.QURAN_URUT to it.urut)
                }
            }catch (e: SQLiteConstraintException){
                d("PTK", e.localizedMessage)
            }
        }
        hideLoad()
    }

    override fun setDataSql(it: List<Quran>) {
        adapter.clear()
        listQuran.addAll(it)
        listDisplay.addAll(it)
        listDisplay.forEach {
            adapter.add(QuranItem(it))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.quran_menu, menu)

        val searchView = menu.findItem(R.id.menu_search)?.actionView as SearchView
        searchView.queryHint = "Pencarian"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.clear()
                if (p0!!.isNotEmpty()){
                    val search = p0.toLowerCase()
                    listQuran.forEach {
                        if (it.nama.toLowerCase().contains(search) or it.nomor.toLowerCase().contentEquals(search)){
                            adapter.add(QuranItem(it))
                        }
                    }
                }else{
                    listQuran.forEach{
                        adapter.add(QuranItem(it))
                    }
                }
                adapter.notifyDataSetChanged()
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when(item.itemId){
            R.id.menu_search -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }

    override fun showLoad() {
        progressBar3.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        progressBar3.visibility = View.INVISIBLE
    }

    override fun showMsg(str: String) {
        toast(str)
    }

}
