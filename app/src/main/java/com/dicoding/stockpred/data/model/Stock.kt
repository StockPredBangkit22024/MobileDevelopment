package com.dicoding.stockpred.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stock(
    val name: String,
    val logo: String,
    val code: String,
) : Parcelable

