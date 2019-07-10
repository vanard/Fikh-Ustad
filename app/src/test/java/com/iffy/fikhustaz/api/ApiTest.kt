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
}