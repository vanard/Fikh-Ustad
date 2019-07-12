package com.iffy.fikhustaz.view

import com.iffy.fikhustaz.data.model.materi.konsulsyariah.Fikih
import com.iffy.fikhustaz.network.RetrofitFactory
import com.iffy.fikhustaz.network.RetrofitService
import com.iffy.fikhustaz.views.fragment.materi.MateriSyariahContract
import com.iffy.fikhustaz.views.fragment.materi.MateriSyariahPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MateriPresenterTest {
    @Mock
    private
    lateinit var view: MateriSyariahContract.View

    @Mock
    private lateinit var apiService: RetrofitService

    @Mock
    private lateinit var retrofit: RetrofitService

    @Mock
    private lateinit var presenter: MateriSyariahPresenter

    @Mock
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        MockitoAnnotations.initMocks(this)
        presenter = MateriSyariahPresenter(view)
        retrofit = RetrofitFactory.makeRetrofitService(RetrofitFactory.BASE_URL_SYARIAH)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun getKitabHaditsTest() {
        val events: MutableList<Fikih> = mutableListOf()
        val response = apiService.fetchFikih(1)

        lazy {
            Mockito.`when`(
                retrofit.fetchFikih(1)
            ).thenReturn(response)

            presenter.getData(1)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).initData(events)
            Mockito.verify(view).hideLoading()
        }

    }
}