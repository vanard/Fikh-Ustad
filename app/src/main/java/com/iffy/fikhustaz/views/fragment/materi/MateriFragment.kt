package com.iffy.fikhustaz.views.fragment.materi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.MateriItem
import com.iffy.fikhustaz.data.model.materi.IslamData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_materi.*
import org.jetbrains.anko.support.v4.toast


class MateriFragment : Fragment(), MateriContract.View {

    val presenter = MateriPresenter(this)
    val adapter = GroupAdapter<ViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materi, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_materi.adapter = adapter
        rv_materi.layoutManager = LinearLayoutManager(context)
        rv_materi.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        presenter.getData()

    }

    override fun showMsg(msg: String) {
        toast(msg)
    }

    override fun initData(list: List<IslamData>) {
        if (list.isNotEmpty()){
            adapter.clear()
            list.forEach {
                adapter.add(MateriItem(it))
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }
}
