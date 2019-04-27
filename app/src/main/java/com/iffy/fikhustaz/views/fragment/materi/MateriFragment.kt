package com.iffy.fikhustaz.views.fragment.materi

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.MateriFikhItem
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.Fikih
import com.iffy.fikhustaz.data.model.materi.konsulsyariah.IsiFikh
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_materi.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import org.jetbrains.anko.support.v4.toast


class MateriFragment : Fragment(), MateriSyariahContract.View {

    val presenter = MateriSyariahPresenter(this)
    val adapter = GroupAdapter<ViewHolder>()
    private var listFikh = mutableListOf<Fikih>()
    private var isiFikh = mutableListOf<IsiFikh>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_materi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).supportActionBar?.title = "Materi"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_materi.adapter = adapter
        rv_materi.layoutManager = LinearLayoutManager(context)
        rv_materi.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        presenter.getData(1)

    }

    override fun listData(fikh: IsiFikh) {
        adapter.clear()
        isiFikh.add(fikh)
        isiFikh.forEachIndexed { index, fikih ->
            adapter.add(MateriFikhItem(listFikh[index],fikih))
        }
    }

    override fun initData(list: List<Fikih>) {
        isiFikh.clear()
        listFikh.clear()

        if (list.isNotEmpty()){
            listFikh.addAll(list)
            listFikh.forEach {
                presenter.getDetailData(it.link)
            }
        }

    }

    override fun showMsg(msg: String) {
        toast(msg)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
    }
}
