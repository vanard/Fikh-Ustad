package com.iffy.fikhustaz.views.fragment.quran


import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.QuranItem
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_quran.*
import org.jetbrains.anko.support.v4.toast

class QuranFragment : Fragment(), QuranContract.View {

    override fun setData(it: List<Quran>) {
        adapter.clear()
        listQuran.addAll(it)
        listDisplay.addAll(it)
        listQuran.forEach {
            adapter.add(QuranItem(it))
        }
    }

    private lateinit var dialog: ProgressDialog
    val presenter = QuranPresenter(this)
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

        (activity as HomeActivity).supportActionBar?.title = "Al - Qur'an"

        rv_quran.adapter = adapter
        rv_quran.layoutManager = LinearLayoutManager(context)
        rv_quran.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.initData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.quran_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return (when(item.itemId){
            R.id.menu_search -> {
                toast("Search Coming Soon")
                true
            }
            else -> super.onOptionsItemSelected(item)
        })
    }

    override fun showLoad() {
        dialog = ProgressDialog.show(this@QuranFragment.context, "", "Loading")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoad() {
        dialog.dismiss()
    }

}
