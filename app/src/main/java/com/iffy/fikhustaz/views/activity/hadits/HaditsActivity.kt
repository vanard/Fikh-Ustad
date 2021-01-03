package com.iffy.fikhustaz.views.activity.hadits

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.HaditsItem
import com.iffy.fikhustaz.data.model.hadist.Hadist
import com.iffy.fikhustaz.data.model.hadist.KitabHadist
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.activity_hadits.*
import org.jetbrains.anko.toast

class HaditsActivity : AppCompatActivity(), HaditsContract.View {

    private lateinit var presenter: HaditsPresenter
    private var page = 1
    private var pageAll = 1
    private lateinit var data: KitabHadist
    private lateinit var dialog: ProgressDialog
    val mAdapter = GroupAdapter<ViewHolder>()
    private lateinit var jumlahHadits: String
    private var pages : Int = 0
    private var listHadits = mutableListOf<Hadist>()

    var isScrolling = false
    var visibleItems = 0
    var totalItems = 0
    var scrolledOutItems = 0
    private var layManager: LinearLayoutManager? = null

    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) isScrolling = true
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (layManager == null) {
                layManager = recyclerView.layoutManager as LinearLayoutManager
            }

            visibleItems = layManager!!.childCount
            totalItems = layManager!!.itemCount
            scrolledOutItems = layManager!!.findFirstVisibleItemPosition()

            if (isScrolling && (visibleItems + scrolledOutItems == totalItems)){

                if (progressBar.visibility == View.VISIBLE) return

                progressBar.visibility = View.VISIBLE
                if (page != pages)
                    presenter.updateData(data.code_kitab, "${page++}")
                else toast("Last Page")
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hadits)

        presenter = HaditsPresenter(this)
        if (intent != null)
            data = intent.getParcelableExtra("data")!!
        else
            toast("Something went wrong.")

        jumlahHadits = data.jumlah.split(" ")[0]
        pages = jumlahHadits.toInt()/15

        supportActionBar?.title = data.kitab

        layManager = LinearLayoutManager(this@HaditsActivity)

        rv_hadits.apply {
            layoutManager = layManager
            adapter = mAdapter
            addOnScrollListener(scrollListener)
        }

        presenter.initData(data.code_kitab, "${page++}")
        presenter.loadAllData(data.code_kitab, "${pageAll++}")
    }

    override fun updateData(hadist: List<Hadist>) {
        hadist.forEach {
            mAdapter.add(HaditsItem(it))
        }
        progressBar.visibility = View.GONE
    }

    override fun loadData(hadits: List<Hadist>) {
        listHadits.addAll(hadits)
        if (page != pages) presenter.loadAllData(data.code_kitab, "${pageAll++}")
        else return
    }

    override fun showLoad() {
        dialog = ProgressDialog.show(this@HaditsActivity, "", "Loading")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoad() {
        dialog.dismiss()
    }

    override fun setData(hadist: List<Hadist>) {
        mAdapter.clear()
        hadist.forEach {
            mAdapter.add(HaditsItem(it))
        }
    }

}
