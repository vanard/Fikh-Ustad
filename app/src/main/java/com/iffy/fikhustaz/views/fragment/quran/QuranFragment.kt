package com.iffy.fikhustaz.views.fragment.quran


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.iffy.fikhustaz.R
import com.iffy.fikhustaz.data.itemviews.QuranItem
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.network.RetrofitFactory
import com.iffy.fikhustaz.views.activity.HomeActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.fragment_materi.*
import kotlinx.android.synthetic.main.fragment_quran.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class QuranFragment : Fragment() {

    val adapter = GroupAdapter<ViewHolder>()
    private var listQuran = mutableListOf<Quran>()

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val service = RetrofitFactory.makeRetrofitService(RetrofitFactory.BASE_URL_SYARIAH)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as HomeActivity).supportActionBar?.title = "Quran"

        rv_quran.adapter = adapter
        rv_quran.layoutManager = LinearLayoutManager(context)
        rv_quran.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        uiScope.launch {
            val request = service.fetchQuran()
            try {
                val response = request.await()
                if (response.isSuccessful){
                    response.body()?.let {
                        initData(it)
                    }
                }else{
                    Log.d("MateriSyariah", "${response.errorBody()}")
                }
            } catch (e: HttpException) {
                Log.d("MateriSyariah", e.code().toString())
            } catch (e: Throwable) {
                Log.d("MateriSyariah", "$e")
            }

        }
    }

    private fun initData(it: List<Quran>) {
        adapter.clear()
        listQuran.addAll(it)
        listQuran.forEach {
            adapter.add(QuranItem(it))
        }
    }

}
