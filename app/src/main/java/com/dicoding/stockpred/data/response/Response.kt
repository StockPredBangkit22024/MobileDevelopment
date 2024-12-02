package com.dicoding.stockpred.data.response

import com.google.gson.annotations.SerializedName

data class Response(

    @field:SerializedName("prediction")
    val prediction: Prediction? = null
)

data class Prediction(

    @field:SerializedName("JPFA")
    val jPFA: String? = null,

    @field:SerializedName("PWON")
    val pWON: String? = null,

    @field:SerializedName("INDF")
    val iNDF: String? = null,

    @field:SerializedName("KLBF")
    val kLBF: String? = null,

    @field:SerializedName("DUTI")
    val dUTI: String? = null,

    @field:SerializedName("ITMG")
    val iTMG: String? = null,

    @field:SerializedName("ICBP")
    val iCBP: String? = null,

    @field:SerializedName("ULTJ")
    val uLTJ: String? = null,

    @field:SerializedName("ASII")
    val aSII: String? = null,

    @field:SerializedName("JSMR")
    val jSMR: String? = null,

    @field:SerializedName("BSDE")
    val bSDE: String? = null,

    @field:SerializedName("SMAR")
    val sMAR: String? = null,

    @field:SerializedName("ACES")
    val aCES: String? = null,

    @field:SerializedName("JRPT")
    val jRPT: String? = null,

    @field:SerializedName("SMSM")
    val sMSM: String? = null,

    @field:SerializedName("EPMT")
    val ePMT: String? = null,

    @field:SerializedName("TSPC")
    val tSPC: String? = null,

    @field:SerializedName("TLKM")
    val tLKM: String? = null,

    @field:SerializedName("CTRA")
    val cTRA: String? = null,

    @field:SerializedName("SMCB")
    val sMCB: String? = null
)
