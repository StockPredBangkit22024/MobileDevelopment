package com.dicoding.stockpred.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PredictResponse(
    val prediction: Map<String, Double>

)

@Parcelize
data class StockPrediction(
    val name: String,
    val code: String,
    val logo: String,
    val predictedPrice: String
) : Parcelable
