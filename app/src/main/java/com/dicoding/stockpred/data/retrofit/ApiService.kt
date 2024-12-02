package com.dicoding.stockpred.data.retrofit

import com.dicoding.stockpred.data.request.PredictRequest
import com.dicoding.stockpred.data.response.PredictResponse
import com.dicoding.stockpred.data.response.StockResponseItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("stocks/details")
    fun getStocks(): Call<List<StockResponseItem>>

    @POST("predict")
    fun predictStock(@Body requestBody: PredictRequest): Call<PredictResponse>
}

