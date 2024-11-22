package com.dicoding.stockpred

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stock(
    val name: String,
    val description: String,
    val photo: Int,
    val price: String
) : Parcelable
