package com.iffy.fikhustaz.views.fragment.materi

import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.MateriFikhItem
import com.iffy.fikhustaz.data.itemviews.QuranItem
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
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_materi.adapter = adapter
        rv_materi.layoutManager = LinearLayoutManager(context)
        rv_materi.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        isiFikh.clear()
        listFikh.clear()

        presenter.getData(1)

        adapter.clear()
    }

    override fun listData(fikh: IsiFikh) {
        isiFikh.add(fikh)
//        isiFikh.forEachIndexed { index, fikih ->
            adapter.add(MateriFikhItem(fikh))
//        }
    }

    override fun initData(list: List<Fikih>) {

        if (list.isNotEmpty()){
            listFikh.addAll(list)
            listFikh.forEach {
                presenter.getDetailData(it.link)
            }
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
                    isiFikh.forEach {
                        if (it.title.toLowerCase().contains(search) or it.category.toLowerCase().contains(search)){
                            adapter.add(MateriFikhItem(it))
                        }
                    }
                }else{
                    isiFikh.forEach{
                        adapter.add(MateriFikhItem(it))
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
