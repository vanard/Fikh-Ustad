package com.iffy.fikhustaz.view

import com.iffy.fikhustaz.data.model.hadist.KitabHadist
import com.iffy.fikhustaz.network.RetrofitFactory
import com.iffy.fikhustaz.network.RetrofitService
import com.iffy.fikhustaz.views.fragment.hadits.HadistContract
import com.iffy.fikhustaz.views.fragment.hadits.HadistPresenter
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
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class HaditsPresenterTest{
    @Mock
    private
    lateinit var view: HadistContract.View

    @Mock
    private lateinit var apiService: RetrofitService

    @Mock
    private lateinit var retrofit: RetrofitService

    @Mock
    private lateinit var presenter: HadistPresenter

    @Mock
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        MockitoAnnotations.initMocks(this)
        presenter = HadistPresenter(view)
        retrofit = RetrofitFactory.makeRetrofitService(RetrofitFactory.BASE_URL_SYARIAH)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun getKitabHaditsTest() {
        val events: MutableList<KitabHadist> = mutableListOf()
        val response = apiService.fetchKitab()

        lazy {
            Mockito.`when`(
                retrofit.fetchKitab()
            ).thenReturn(response)

            presenter.initData()

            verify(view).showLoad()
            verify(view).setData(events)
            verify(view).hideLoad()
        }

    }
}