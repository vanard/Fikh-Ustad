package com.iffy.fikhustaz.views.fragment.hadits


import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.KitabItem
import com.iffy.fikhustaz.data.model.hadist.KitabHadist
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.iffy.fikhustaz.views.activity.hadits.HaditsActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_hadist.*
import org.jetbrains.anko.support.v4.startActivity

class HadistFragment : Fragment(), HadistContract.View {

    val adapter = GroupAdapter<ViewHolder>()

    override fun showLoad() {
        progressBar2.visibility = View.VISIBLE
    }

    override fun hideLoad() {
        progressBar2.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hadist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val presenter = HadistPresenter(this)
        (activity as HomeActivity).supportActionBar?.title = "Hadist"

        rv_hadist.adapter = adapter
        rv_hadist.layoutManager = LinearLayoutManager(context)
        rv_hadist.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        presenter.initData()

    }

    private val onItemClick = OnItemClickListener { it, view ->
        if (it is KitabItem) {
                startActivity<HaditsActivity>(
                    "data" to it.hadist
                )

        }
    }

    override fun setData(kitab: List<KitabHadist>) {
        adapter.clear()
        kitab.forEach {
            adapter.apply {
                setOnItemClickListener(onItemClick)
                add(KitabItem(it))
            }
        }
    }

}
