package com.iffy.fikhustaz.view

import android.content.Context
import com.iffy.fikhustaz.data.model.quran.Quran
import com.iffy.fikhustaz.network.RetrofitFactory
import com.iffy.fikhustaz.network.RetrofitService
import com.iffy.fikhustaz.views.fragment.quran.QuranContract
import com.iffy.fikhustaz.views.fragment.quran.QuranPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class QuranPresenterTest {
    @Mock
    private
    lateinit var test: Context

    @Mock
    private
    lateinit var view: QuranContract.View

    @Mock
    private lateinit var apiService: RetrofitService

    @Mock
    private lateinit var retrofit: RetrofitService

    @Mock
    private lateinit var presenter: QuranPresenter

    @Mock
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)

        MockitoAnnotations.initMocks(this)
        presenter = QuranPresenter(view, test)
        retrofit = RetrofitFactory.makeRetrofitService(RetrofitFactory.BASE_URL_SYARIAH)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun quranGetSurahTest() {
        val events: MutableList<Quran> = mutableListOf()
        val response = apiService.fetchQuran()

        lazy {
            Mockito.`when`(
                retrofit.fetchQuran()
            ).thenReturn(response)

            presenter.initData()

            verify(view).showLoad()
            verify(view).setData(events)
            verify(view).hideLoad()
        }

    }
}