package com.iffy.fikhustaz.api

import com.iffy.fikhustaz.network.RetrofitService
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.verify

class ApiTest {
    @Mock
    private lateinit var apiRest : RetrofitService

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)

        apiRest = mock(RetrofitService::class.java)
    }
    @Test
    fun getKitabTest(){
        apiRest.fetchKitab()
        verify(apiRest).fetchKitab()
    }
    @Test
    fun getHaditsTest(){
        apiRest.fetchHadist("abu-daud", "1")
        verify(apiRest).fetchHadist("abu-daud", "1")
    }
    @Test
    fun getQuranTest(){
        apiRest.fetchQuran()
        verify(apiRest).fetchQuran()
    }
    @Test
    fun getSurahTest(){
        apiRest.fetchSurat("1")
        verify(apiRest).fetchSurat("1")
    }
    @Test
    fun getMateriTest(){
        apiRest.fetchFikih(1)
        verify(apiRest).fetchFikih(1)
    }
    @Test
    fun getDetailMateriTest(){
        apiRest.fetchIsiFikh("35143-jual-beli-curang-dan-mengurangi-timbangan-bagaimana-cara-bertaubatnya")
        verify(apiRest).fetchIsiFikh("35143-jual-beli-curang-dan-mengurangi-timbangan-bagaimana-cara-bertaubatnya")
    }
}