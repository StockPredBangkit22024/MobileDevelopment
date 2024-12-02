package com.dicoding.stockpred.data.response

import com.google.gson.annotations.SerializedName

data class StockResponseItem(

    @field:SerializedName("website")
    val website: String,

    @field:SerializedName("code")
    val code: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("logo")
    val logo: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("sector")
    val sector: String
)
