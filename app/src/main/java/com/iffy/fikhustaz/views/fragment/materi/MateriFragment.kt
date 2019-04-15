package com.iffy.fikhustaz.views.fragment.materi

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.MateriItem
import com.iffy.fikhustaz.data.model.materi.Code
import com.iffy.fikhustaz.data.model.materi.IslamData
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_materi.*
import kotlinx.coroutines.Job
import org.jetbrains.anko.support.v4.toast


class MateriFragment : Fragment(), MateriContract.View {

    val presenter = MateriPresenter(this)
    val adapter = GroupAdapter<ViewHolder>()
    private lateinit var dialog: ProgressDialog

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

        presenter.getData()

    }

    override fun initData(list: List<IslamData>) {
        d("MateriFragment", "$list")
        if (list.isNotEmpty()){
            adapter.clear()
            list.forEach {
                adapter.add(MateriItem(it))
            }
            adapter.notifyDataSetChanged()
            presenter.getDataFatwa()
        }
    }

    override fun initDataFatwa(list: List<IslamData>) {
        if (list.isNotEmpty()){
            list.forEach {
                adapter.add(MateriItem(it))
            }
            adapter.notifyDataSetChanged()
        }
    }

    override fun showMsg(msg: String) {
        toast(msg)
    }

    override fun showLoading() {
        dialog = ProgressDialog.show(this@MateriFragment.context, "", "Loading materi data")
        dialog.setCancelable(false)
        dialog.isIndeterminate
    }

    override fun hideLoading() {
        dialog.dismiss()
    }
}
