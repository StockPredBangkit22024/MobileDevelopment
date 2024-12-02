package com.dicoding.stockpred.data.request

data class PredictRequest(
    val exchange_rate: Double,
    val bi_rate: Double,
    val inflation_rate: Double
)
